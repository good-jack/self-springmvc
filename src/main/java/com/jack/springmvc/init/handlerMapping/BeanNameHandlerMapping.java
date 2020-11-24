package com.jack.springmvc.init.handlerMapping;

import com.jack.springmvc.controller.Controller;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 实现 ”/beanName“ 作为bean id 的方式
 * @author: zhenghao
 * @date: 2020/11/19 17:31
*/
@Component
public class BeanNameHandlerMapping  implements HandlerMapping,InstantiationAwareBeanPostProcessor {

    public static Map<String, Controller> map = new HashMap<>();

    /**
     * @Description: spring 的后置处理器的方法
     * @author: zhenghao
     * @date: 2020/11/19 17:32
    */

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(beanName.startsWith("/")){
            map.put(beanName,(Controller)bean);
        }
        return true;
    }

    @Override
    public Object getHandlerMapping(String requestURI) {
        return map.get(requestURI);
    }
}
