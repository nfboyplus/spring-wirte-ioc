package com.ioc.cn;

import com.ioc.cn.entity.User;
import com.ioc.cn.service.UserService;
import com.ioc.cn.util.ExtClassPathXmlApplicationContext;

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
