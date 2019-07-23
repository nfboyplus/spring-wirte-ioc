package com.ioc.cn.util;

/**
 * 手写Spring专题 XML方式注入bean
 */
public class ClassPathXmlApplicationContext {

    // xml 路径地址
    private String xmlPath;

    public ClassPathXmlApplicationContext(String xmlPath){
        this.xmlPath = xmlPath;
    }


}
