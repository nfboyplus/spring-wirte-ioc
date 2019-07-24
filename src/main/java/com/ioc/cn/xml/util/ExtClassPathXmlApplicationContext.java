package com.ioc.cn.xml.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 手写Spring专题 XML方式注入bean
 */
public class ExtClassPathXmlApplicationContext {

    /**
     *  xml 路径地址
      */
    private String xmlPath;

    public ExtClassPathXmlApplicationContext(String xmlPath){
        this.xmlPath = xmlPath;
    }

    /**
     * 通过 xml 里的 beanId 获取 Bean
     */
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)){
            throw new Exception("---- beanId不能为空 ----");
        }
        //1.读取配置文件
        List<Element> elements = readerXml();
        if (null == elements){
            throw new Exception("---- 该配置文件没有子元素 ----");
        }
        //2.使用beanId查找对应的class地址
        String beanClass = findXmlByIdClass(elements, beanId);
        if (StringUtils.isEmpty(beanClass)){
            throw new Exception("---- 未找到对应的class地址 ----");
        }
        //3.使用反射机制初始化对象
        Class<?> forName = Class.forName(beanClass);
        return forName.newInstance();
    }

    /**
     * 读取配置文件信息
     */
    private List<Element> readerXml() throws Exception {
        SAXReader saxReader = new SAXReader();
        if (StringUtils.isEmpty(xmlPath)) {
            throw new Exception("---- xml路径为空 ----");
        }
        Document read = saxReader.read(getClassXmlInputStream(xmlPath));
        //获取跟节点信息
        Element rootElement = read.getRootElement();
        //获取子节点
        List<Element> elements = rootElement.elements();
        if (elements == null || elements.isEmpty()){
            return null;
        }
        return elements;
    }

    /**
     * 使用beanId查找该Class地址
     */
    private String findXmlByIdClass(List<Element> elements, String beanId) throws Exception {
        for (Element element : elements){
            //读取节点上是否有 value
            String beanIdValue = element.attributeValue("id");
            if (StringUtils.isEmpty(beanIdValue)){
                throw new Exception("---- 使用该beanId为查找到元素 ----");
            }
            if (!beanIdValue.equals(beanId)){
                continue;
            }
            //获取Class地址属性
            String classPath = element.attributeValue("class");
            if (StringUtils.isNotEmpty(classPath)){
                return classPath;
            }
        }
        return null;
    }

    /**
     * 读取 xml 配置文件
     */
    private InputStream getClassXmlInputStream(String xmlPath) {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(xmlPath);
        return resourceAsStream;
    }

}
