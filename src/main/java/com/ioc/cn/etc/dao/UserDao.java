package com.ioc.cn.etc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * created on 2019/7/22 9:25
 *
 * @author nfboy_liusong@163.com
 * @version 1.0.0
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(Integer id, String name, Integer age) {
        String sql = "INSERT INTO test_user (id, name, age) VALUES(?,?,?);";
        int updateResult = jdbcTemplate.update(sql, id, name, age);
        System.out.println("updateResult:" + updateResult);
    }

    public void insert(Integer id, String name, Integer age) {
        String sql = "INSERT INTO test_user (id, name, age) VALUES(?,?,?);";
        int updateResult = jdbcTemplate.update(sql, id, name, age);
        System.out.println("加入事务处理，updateResult:" + updateResult);
    }

}
