package com.ioc.cn.etc.service.impl;

import com.ioc.cn.etc.annotation.ExtService;
import com.ioc.cn.etc.dao.UserDao;
import com.ioc.cn.etc.service.OrderService;
import com.ioc.cn.etc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * created on 2019/7/22 9:34
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
@ExtService
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Resource //使用该注解获取不到
    private OrderService orderService;

    @Override
    public void add() {
        userDao.add(1, "刘松", 28);
        System.out.println("---- 向数据库添加数据 ----");
        // 没有加入事务处理之前：抛出异常，不继续执行，但是上面的数据插入到数据库，没有回滚；
        int i = 10/0;
        userDao.add(2, "刘刘",18);
    }

    /**
     * 声明式事务：@Transactional
     */
    @Transactional
    @Override
    public void insert() {
        userDao.insert(1, "刘松", 28);
        System.out.println("---- 向数据库添加数据 ----");
        // 加入事务：抛出异常，不继续执行，上面的数据未插入到数据库，数据回滚；
        int i = 10/0;
        userDao.insert(2, "刘刘",18);
    }

    /**
     * 此处的 orderService 使用的是 @Resource 注入，和 @ExtService 注入的不是同一个容器，抛出异常；
     */
    @Override
    public void test() {
        System.out.println("---- 测试自定义注解@ExtService,注入Bean ----");
        String param = orderService.insert();
        System.out.println(param);
    }

    /**
     * 注意：开启事务的时候，不要 try catch，将异常抛给外层AOP处理；
     */


}
