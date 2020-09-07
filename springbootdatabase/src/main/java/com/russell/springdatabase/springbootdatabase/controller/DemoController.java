package com.russell.springdatabase.springbootdatabase.controller;
import com.russell.springdatabase.springbootdatabase.entity.User;
import com.russell.springdatabase.springbootdatabase.service.DemoService;
import com.russell.springdatabase.springbootdatabase.service.serviceImp.ServiceImp;
import com.russell.springdatabase.springbootdatabase.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class DemoController {

    @Autowired
    private DemoService service;

//    @GetMapping("")
//    @ResponseBody
//    private Object index() {
//        return service.findAll();
//    }

    // 必须要用这种格式才能渲染HTML页面
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @GetMapping(value = "/getLogin")
    public String getLogin() {
        return "login";
    }

    @GetMapping(value = "/getIndex")
    public ModelAndView getIndex(HttpSession session) {
        String username = (String) session.getAttribute("userName");
        return new ModelAndView("index").addObject("username",username);
    }

    @PostMapping(value = "/login")
    public ModelAndView login(String username, String password, HttpSession session) {
        System.out.println(username);
        System.out.println(password);
        service.login(username, password);
        //判断用户密码是否正确
        if (service.login(username, password).getCode() == 500) {
            System.out.println(service.login(username, password).toString());
            session.invalidate();
            ModelAndView modelAndView = new ModelAndView("login");
            return modelAndView.addObject("hello", "false");
        } else {
            System.out.println(service.login(username, password).toString());
            session.setAttribute("userName", username);
            session.setAttribute("password", password);
            String name = (String) session.getAttribute("userName");
            System.out.println(name);
            return new ModelAndView("index").addObject("username",name);
        }
    }

    @GetMapping(value = "/getRegister")
    public String getRegister() {
        return "register";
    }

    @PostMapping(value = "/register")
    public ModelAndView register(String username, String password, int age, String sex, String telephone,HttpSession session) {
        Result result = new Result();
        result = service.register(username, password, age, sex, telephone);
        if (result.getCode() == 500) {
            ModelAndView modelAndView = new ModelAndView("register");
            return modelAndView.addObject("fail", "failure");
        } else {
            System.out.println(result.toString());
            int previousAge = service.getAge(username);
            String previousNumber = service.getNumber(username);
            //注册成功之后返回到index界面并且跳出成功提示
            String username1 = (String) session.getAttribute("userName");
            ModelAndView modelAndView = new ModelAndView("index");
            return modelAndView.addObject("username",username1)
                    .addObject("succeed", "success");
        }

    }


    @GetMapping(value = "/getEdit")
    public ModelAndView getEdit(HttpSession session) {
        String username = (String) session.getAttribute("userName");
        int previousAge = service.getAge(username);
        String previousNumber = service.getNumber(username);
        return new ModelAndView("editPerson").addObject("age", previousAge)
                .addObject("number", previousNumber);
    }

    @PostMapping(value = "/editAge")
    public ModelAndView editAge(String age, HttpSession session) {
        String username = (String) session.getAttribute("userName");
        service.modifyAge(Integer.parseInt(age), username);
        int previousAge = service.getAge(username);
        String previousNumber = service.getNumber(username);
        return new ModelAndView("editPerson").addObject("age", previousAge)
                .addObject("number", previousNumber)
                .addObject("ageSuccess", "ageSuccess");
    }

    @PostMapping(value = "/editNumber")
    public ModelAndView editNumber(String telephone, HttpSession session) {
        String username = (String) session.getAttribute("userName");
        service.modifyNumber(telephone, username);
        int previousAge = service.getAge(username);
        String previousNumber = service.getNumber(username);
        return new ModelAndView("editPerson").addObject("age", previousAge)
                .addObject("number", previousNumber)
                .addObject("numSuccess", "numSuccess");
    }

    @PostMapping(value = "/editPsw")
    public ModelAndView editPsw(String password, String passwordNew, HttpSession session) {
        String username = (String) session.getAttribute("userName");
        String psw = (String) session.getAttribute("password");
        int previousAge = service.getAge(username);
        String previousNumber = service.getNumber(username);
        if (psw.equals(password)) {
            service.modifyPsw(passwordNew, username);
            System.out.println(service.modifyPsw(passwordNew, username).toString());
            return new ModelAndView("editPerson").addObject("age", previousAge)
                    .addObject("number", previousNumber)
                    .addObject("pswSuccess", "pswSuccess");
        } else {
            System.out.println("改密失败");
            return new ModelAndView("editPerson").addObject("age", previousAge)
                    .addObject("number", previousNumber)
                    .addObject("pswSuccess", "pswFail");
        }
    }


    @PostMapping(value = "/editPerson")
    public ModelAndView editPerson(Integer age, String telephone, String password, HttpSession session) {
        Result result = new Result();
        String username = (String) session.getAttribute("userName");
        System.out.println(username);
        int previousAge = service.getAge(username);
        String number = service.getNumber(username);
        result = service.modifyAge(age, username);
        if (result.getCode() == 500) {
            ModelAndView modelAndView = new ModelAndView("editPerson");
            return modelAndView.addObject("age", previousAge);
        } else {
            System.out.println(result.toString());
        }
        ModelAndView modelAndView = new ModelAndView("editPerson");
        return modelAndView.addObject("age", previousAge).addObject("number", number);
    }

}