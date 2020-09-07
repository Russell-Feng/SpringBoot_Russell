package com.russell.springdatabase.springbootdatabase.service.serviceImp;

import com.russell.springdatabase.springbootdatabase.dao.UserRepository;
import com.russell.springdatabase.springbootdatabase.entity.User;
import com.russell.springdatabase.springbootdatabase.service.DemoService;
import com.russell.springdatabase.springbootdatabase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImp implements DemoService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public Result login(String name, String password) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(500);
        try {
            String userId = userRepository.getIdByNameAndPsw(name, password);
            if (userId == null) {
                result.setMsg("用户名或密码错误");
            } else {
                result.setMsg("登录成功");
                result.setSuccess(true);
                User user = new User();
                user.setId(Integer.parseInt(userId));
                result.setCode(200);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;


    }

    @Override
    public Result register(String name, String password, int age, String sex, String telephone) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(500);
        try {
            String userId = userRepository.getIdByName(name);
            if (userId != null) {
                result.setMsg("用户名已存在，请更换用户名！");
            } else {
                userRepository.register(name, password, age, sex, telephone);
                result.setMsg("注册成功！");
                result.setCode(200);
                result.setSuccess(true);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer getAge(String name) {
        String age = userRepository.getAgeByName(name);
        return Integer.valueOf(age);
    }

    @Override
    public String getNumber(String name) {
        String number = userRepository.getNumberByName(name);
        return number;
    }

    @Override
    public Result modifyAge(int age, String name) {
        Result result = new Result();
        userRepository.modifyAgeByName(age, name);
        result.setMsg("更改年龄成功！");
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result modifyNumber(String telephone, String name) {
        Result result = new Result();
        userRepository.modifyTeleByName(telephone, name);
        result.setMsg("更改号码成功！");
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result modifyPsw(String password, String name) {
        Result result = new Result();
        userRepository.modifyPswByName(password, name);
        result.setMsg("更改密码成功！");
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }
}
