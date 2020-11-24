package com.jack.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface Controller {

    Object handler(HttpServletRequest request, HttpServletResponse response);
}
