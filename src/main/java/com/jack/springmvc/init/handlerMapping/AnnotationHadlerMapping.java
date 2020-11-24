package com.jack.springmvc.init.handlerMapping;

import com.jack.springmvc.annotation.RequestMapping;
import com.jack.springmvc.servlet.RequestMappingInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class AnnotationHadlerMapping  implements HandlerMapping,InstantiationAwareBeanPostProcessor {

    public static Map<String,RequestMappingInfo> map = new HashMap<>();

    @Override
    //bean里面会有多个处理器
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        //这里需要判断 bean上面是否有controller注解
        Controller annotation = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        if (annotation !=null){
            //通过反射获得 bean里面的所有方法
            Method[] methods = bean.getClass().getDeclaredMethods();
            for(Method method : methods){
                RequestMappingInfo requestMappingInfo = createRequestMappingInfo(method,bean);
                map.put(requestMappingInfo.getUri(),requestMappingInfo);
            }
        }
        return true;

    }

    /**
     * @Description: 将bean中 有requestMapping注解的的方法 放在map中
     * @author: zhenghao
     * @date: 2020/11/19 18:01
    */
    private RequestMappingInfo createRequestMappingInfo(Method method,Object bean) {
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){
            requestMappingInfo.setMethod(method);
            requestMappingInfo.setUri(method.getDeclaredAnnotation(RequestMapping.class).value());
            requestMappingInfo.setObj(bean);
        }
        return requestMappingInfo;
    }

    @Override
    public Object getHandlerMapping(String requestURI) {
        return map.get(requestURI);
    }
}
