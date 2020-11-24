package com.jack.springmvc.controller;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/testController")//通过id定义url
public class TestController implements Controller{

    @Override
    public Object handler(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("TestController");
        return null;
    }
}
