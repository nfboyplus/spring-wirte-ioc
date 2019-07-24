package com.ioc.cn.etc.service.impl;

import com.ioc.cn.etc.annotation.ExtService;
import com.ioc.cn.etc.service.OrderService;

/**
 * created on 2019/7/24 15:46
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
@ExtService
public class OrderServiceImpl implements OrderService {

    @Override
    public String  insert() {
        String param = "找不到";
        System.out.println("---- 测试能否找到该方法 ----");
        return param;
    }
}
