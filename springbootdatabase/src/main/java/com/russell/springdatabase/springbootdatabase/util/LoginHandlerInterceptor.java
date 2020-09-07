package com.russell.springdatabase.springbootdatabase.util;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取登录时保存到session的用户信息
//        SysUser user = (SysUser) request.getSession().getAttribute("user");
        Object user = request.getSession().getAttribute("userName");
        if (user == null) {
            //拦截未登录请求,跳转到登录页面
            System.out.println("没有权利");
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
