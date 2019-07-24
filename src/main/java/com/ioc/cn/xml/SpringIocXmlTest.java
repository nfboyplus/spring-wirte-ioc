package com.ioc.cn.xml;

import com.ioc.cn.xml.util.XmlUtils;
import org.dom4j.DocumentException;

/**
 * 使用dom4j解析xml
 */
public class SpringIocXmlTest {

    public static void main(String[] args) throws DocumentException {
        XmlUtils xmlUtils = new XmlUtils();
        xmlUtils.test();

    }
}
