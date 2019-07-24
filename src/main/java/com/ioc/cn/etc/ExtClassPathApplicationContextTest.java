package com.ioc.cn.etc;

import com.ioc.cn.etc.service.UserService;
import com.ioc.cn.etc.util.ExtClassPathXmlApplicationContext;

/**
 * 手写SpringIOC 自定义注解，service 注入 Bean
 */
public class ExtClassPathApplicationContextTest {

    public static void main(String[] args) throws Exception {
        ExtClassPathXmlApplicationContext applicationContext = new ExtClassPathXmlApplicationContext("com.ioc.cn.etc.service");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
        userService.test();
    }
}
