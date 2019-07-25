package com.ioc.cn.etc.service.impl;

import com.ioc.cn.etc.annotation.ExtResource;
import com.ioc.cn.etc.annotation.ExtService;
import com.ioc.cn.etc.service.OrderService;
import com.ioc.cn.etc.service.UserService;


/**
 * created on 2019/7/24 15:46
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
@ExtService
public class OrderServiceImpl implements OrderService {

    @ExtResource
    private UserService userService;

    @Override
    public void insert() {
        userService.test();
        System.out.println("---- insert ----");
    }
}
