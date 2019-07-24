package com.ioc.cn.xml;

import com.ioc.cn.xml.service.UserService;
import com.ioc.cn.xml.util.ExtClassPathXmlApplicationContext;

/**
 * created on 2019/7/24 9:08
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
public class ExtClassPathXmlApplicationContextTest {

    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext applicationContext = new ExtClassPathXmlApplicationContext("spring-test.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        System.out.println(userService.toString());
    }
}
