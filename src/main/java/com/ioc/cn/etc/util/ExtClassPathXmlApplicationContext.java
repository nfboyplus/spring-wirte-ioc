package com.ioc.cn.etc.util;

import com.ioc.cn.etc.annotation.ExtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解版本注入Bean
 */
public class ExtClassPathXmlApplicationContext {

    /**
     * 扫包范围
     */
    private String packageName;

    ConcurrentHashMap<String, Object> initBean = null;

    public ExtClassPathXmlApplicationContext(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 根据 beanId 查找对象
     */
    public Object getBean(String beanId) throws Exception {
        //1.使用反射机制获取该包下所有的类已经存在bean的注解类
        List<Class> listClassesAnnotation = findClassExitService();
        if (CollectionUtils.isEmpty(listClassesAnnotation)) {
            throw new Exception("---- 没有需要初始化的bean ----");
        }
        //2.使用Java反射机制初始化对象
        initBean = initBean(listClassesAnnotation);
        if (initBean == null || initBean.isEmpty()) {
            throw new Exception("---- 初始化bean为空 ----");
        }
        //3.使用beanID查找查找对应bean对象
        Object object = initBean.get(beanId);
        //4.使用反射读取类的属性,赋值信息
        attriAssign(object);
        return object;
    }

    /**
     * 使用反射机制获取该包下所有的类已经存在bean的注解类
     */
    private List<Class> findClassExitService() throws Exception {
        //1.判断扫包地址是否为空
        if (StringUtils.isEmpty(packageName)) {
            throw new Exception("---- 扫包地址不能为空 ----");
        }
        //2.使用反射机制获取该包下的所有的类
        List<Class<?>> classesByPackageName = ClassUtils.getClasses(packageName);
        //3.存放类上有Bean注入注解
        List<Class> exitClassesAnnotation = new ArrayList<Class>();
        //4.判断该类上属否存在注解
        for (Class classInfo : classesByPackageName) {
            ExtService extService = (ExtService) classInfo.getDeclaredAnnotation(ExtService.class);
            if (extService != null) {
                exitClassesAnnotation.add(classInfo);
                continue;
            }
        }
        return exitClassesAnnotation;
    }


    /**
     * 初始化bean对象
     */

    public ConcurrentHashMap<String, Object> initBean(List<Class> listClassesAnnotation)
            throws InstantiationException, IllegalAccessException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Object>();
        for (Class classInfo : listClassesAnnotation) {
            //初始化对象
            Object newInstance = classInfo.newInstance();
            //获取父类名称,首字母转小写
            String beanId = toLowerCaseFirstOne(classInfo.getSimpleName());
            //加入到Bean容器中
            concurrentHashMap.put(beanId, newInstance);
        }
        return concurrentHashMap;
    }

    /**
     * 首字母转小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 使用反射读取类的属性,赋值信息
     */
    private void attriAssign(Object object) throws IllegalAccessException {
        //1.获取类的属性是否存在,获取bean注解
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();
        for (Field field : declaredFields) {
            //2.获取属性名称
            String name = field.getName();
            //3.使用属性名称查找bean容器赋值
            Object bean = initBean.get(name);
            if (bean != null) {
                //4.私有访问允许访问
                field.setAccessible(true);
                //5.给属性赋值
                field.set(object, bean);
                continue;
            }
        }
    }

}
