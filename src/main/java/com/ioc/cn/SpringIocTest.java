package com.ioc.cn;

import com.ioc.cn.entity.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 手写 SpringIOC 测试
 */
public class SpringIocTest {

    public static void main(String[] args){
        //SpringIOC XML版本使用 dom4j+反射机制
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
    }
}
