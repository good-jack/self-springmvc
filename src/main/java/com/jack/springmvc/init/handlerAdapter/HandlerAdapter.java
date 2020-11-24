package com.jack.springmvc.init.handlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface HandlerAdapter {

    public boolean supports(Object handler);

    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler);
}
