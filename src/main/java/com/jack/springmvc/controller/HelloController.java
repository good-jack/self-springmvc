package com.jack.springmvc.controller;

import com.jack.springmvc.User;
import com.jack.springmvc.annotation.RequestMapping;
import com.jack.springmvc.annotation.RequestParam;
import com.jack.springmvc.annotation.ResponseBody;
import com.jack.springmvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    HelloService service;

    @RequestMapping("/test.do")
    public String test(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam("param") String param){
        System.out.println("param:"+param);
        service.test(param);
        request.setAttribute("param",param);
        return "forward:/test.jsp";
    }

    @RequestMapping("/test1.do")
    @ResponseBody
    public Map test1(String param){
        Map map = new HashMap();
        map.put("param",param);
        return map;
    }

    @RequestMapping("/test2.do")
    @ResponseBody
    public User test2(String param){
        User user = new User();
        user.setName(param);
        user.setAge(param);
        return user;
    }
}
