package com.russell.springdatabase.springbootdatabase.service;

import com.russell.springdatabase.springbootdatabase.entity.User;
import com.russell.springdatabase.springbootdatabase.util.Result;

import java.util.List;

/**
 * 接口层:负责逻辑处理访问数据库层
 */
public interface DemoService {
    Result login(String name,String password);
    Result register(String name,String password,int age,String sex,String telephone);
    Integer getAge(String name);
    String getNumber(String name);
    Result modifyAge(int age, String name);
    Result modifyNumber(String telephone, String name);
    Result modifyPsw(String password, String name);
}
