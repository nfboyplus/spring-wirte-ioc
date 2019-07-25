package com.ioc.cn.etc.util;

import com.ioc.cn.etc.annotation.ExtResource;
import com.ioc.cn.etc.annotation.ExtService;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解版本注入Bean
 */
public class ExtClassPathXmlApplicationContext {

    /**
     * 扫包范围
     */
    private String packageName;

    /**
     * spring bean 容器
     */
    ConcurrentHashMap<String, Object> beans = null;

    public ExtClassPathXmlApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<String, Object>();
        this.packageName = packageName;
        initBeans();
        initEntryField();
    }

    /**
     * 初始化对象
     */
    public void initBeans() throws Exception {
        //使用Java反射机制扫描，获取当前包下所有类
        List<Class<?>> classes = ClassUtil.getClasses(packageName);
        //判断类上是否存在注入的Bean的注解
        ConcurrentHashMap<String, Object> concurrentHashMap = findClassExitAnnotation(classes);
        if (concurrentHashMap == null || concurrentHashMap.isEmpty()){
            throw new Exception("---- 该包下没有任何类加上注解 ----");
        }
    }

    /**
     * 判断类上是否存在注入的 Bean 的注解
     */
    public ConcurrentHashMap<String, Object> findClassExitAnnotation(List<Class<?>> classes) throws Exception {
        for (Class<?> classInfo : classes){
            //判断类上是否有注解
            ExtService extService = classInfo.getAnnotation(ExtService.class);
            if (null != extService){
                //获取当前类名
                String className = classInfo.getSimpleName();
                //将当前类名变成小写
                String beanId = toLowerCaseFirstOne(className);
                Object newInstance = newInstance(classInfo);
                beans.put(beanId, newInstance);
            }
        }
        return beans;
    }

    /**
     * 初始化对象
     */
    public Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
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
     * 初始化属性
     */
    private void initEntryField() throws Exception {
        //遍历所有的Bean容器
        for (Map.Entry<String, Object> entry : beans.entrySet()){
            //判断属性上面是否加注解
            Object bean = entry.getValue();
            //依赖注入原理
            attriAssign(bean);
        }
    }

    /**
     * 依赖注入原理
     */
    private void attriAssign(Object object) throws Exception {
        //1.获取类的属性是否存在,获取bean注解
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();
        //判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            ExtResource extResource = field.getAnnotation(ExtResource.class);
            if (null != extResource){
                //2.获取属性名称
                String beanId = field.getName();
                //3.使用属性名称查找bean容器赋值
                Object bean = getBean(beanId);
                if (bean != null) {
                    //4.私有访问允许访问
                    field.setAccessible(true);
                    //5.给属性赋值
                    field.set(object, bean);
                }
            }
        }
    }

    /**
     * 根据 beanId 查找对象
     */
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)){
            throw new Exception("---- beanId不能为空 ----");
        }
        //从spring 容器获取 bean
        Object object = beans.get(beanId);
        return object;
    }

}
