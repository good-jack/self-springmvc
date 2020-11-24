package com.jack.springmvc.servlet;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: zhenghao
 * @date: 2020/11/19 17:34
*/
public class RequestMappingInfo {

    private Method method;

    private String uri;

    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
