package com.ioc.cn.etc.service.impl;

import com.ioc.cn.etc.annotation.ExtResource;
import com.ioc.cn.etc.annotation.ExtService;
import com.ioc.cn.etc.service.OrderService;
import com.ioc.cn.etc.service.UserService;

/**
 * created on 2019/7/22 9:34
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
@ExtService
public class UserServiceImpl implements UserService {

//    @Resource //使用该注解获取不到
    @ExtResource
    private OrderService orderService;


    /**
     * 此处的 orderService 使用的是 @Resource 注入，和 @ExtService 注入的不是同一个容器，抛出异常；
     */
    @Override
    public void test() {
        System.out.println("---- 测试自定义注解@ExtService,注入Bean ----");
        orderService.insert();
        System.out.println("---- 使用Java反射机制初始化对象 ----");
    }

    /**
     * 注意：开启事务的时候，不要 try catch，将异常抛给外层AOP处理；
     */


}
