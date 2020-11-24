package com.jack.springmvc.init.handlerAdapter;

import com.jack.springmvc.controller.Controller;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BeanNameHandlerAdapter implements HandlerAdapter{
    @Override
//    //判断hanlde是否适配当前适配器
    public boolean supports(Object handler) {
        //判断是否是RequestMapping子类
        return (handler instanceof Controller);
    }

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return ((Controller)handler).handler(request, response);
    }
}
