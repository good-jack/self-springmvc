package com.jack.springmvc.init.handlerAdapter;

import com.alibaba.fastjson.JSON;
import com.jack.springmvc.annotation.RequestParam;
import com.jack.springmvc.annotation.ResponseBody;
import com.jack.springmvc.servlet.RequestMappingInfo;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Component
public class AnnotationHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        //判断是否是RequestMapping子类
        return (handler instanceof RequestMappingInfo);
    }
    @Override
    //参数绑定
    public Object handle(HttpServletRequest request, HttpServletResponse response, Object handler){
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo)handler;
        //请求携带的参数
        Map<String, String[]> paramMap = request.getParameterMap();
        //方法定义的参数
        Method method = requestMappingInfo.getMethod();
        Parameter[] parameters = method.getParameters();

        //参数注入
        Object[] params = new Object[method.getParameterTypes().length];
        for(int i=0; i<parameters.length; i++){
            for(Map.Entry<String, String[]> entry : paramMap.entrySet()){
                if(parameters[i].getAnnotation(RequestParam.class) != null && entry.getKey()!= null &&
                        entry.getKey().equals(parameters[i].getAnnotation(RequestParam.class).value())){
                    params[i] = entry.getValue()[0];
                //jdk1.8实现反射获取方法名   1.8之前使用asm实现
                }else if(entry.getKey().equals(parameters[i].getName())){
                    params[i] = entry.getValue()[0];
                }
            }

            //传入request和response
            if(ServletRequest.class.isAssignableFrom(parameters[i].getType())){
                params[i] = request;
            }else if(ServletResponse.class.isAssignableFrom(parameters[i].getType())){
                params[i] = response;
            }
        }

        try {
            Object result = method.invoke(requestMappingInfo.getObj(),params);
            //现在都前后端分离，所以这个已经很少用了
            if (result instanceof String) {
                if ("forward".equals(((String) result).split(":")[0])) {
                    request.getRequestDispatcher(((String) result).split(":")[1]).forward(request, response);
                } else {
                    response.sendRedirect(((String) result).split(":")[1]);
                }
            }else{
                //返回json结果
                if(method.isAnnotationPresent(ResponseBody.class)){
                    return JSON.toJSONString(result);
                }
            }

            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
