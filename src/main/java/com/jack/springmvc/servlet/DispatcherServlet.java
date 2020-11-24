package com.jack.springmvc.servlet;

import com.jack.springmvc.init.handlerAdapter.HandlerAdapter;
import com.jack.springmvc.init.handlerMapping.HandlerMapping;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    static Collection<HandlerAdapter> handlerAdapters ;
    static Collection<HandlerMapping> handlerMappings ;

    public DispatcherServlet() {
    }

    /**
     * @Description:
     * @author: zhenghao
     * @date: 2020/11/20 10:23
    */
    public DispatcherServlet(AnnotationConfigApplicationContext ac) {
        //tomcat 启动时候完成组件初始化 完成映射器和适配器的功能
        Map<String, HandlerMapping> handlerMappingMaps = ac.getBeansOfType(HandlerMapping.class);
        handlerMappings = handlerMappingMaps.values();

        Map<String, HandlerAdapter> handlerAdapterMaps = ac.getBeansOfType(HandlerAdapter.class);
        handlerAdapters = handlerAdapterMaps.values();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获得映射器
        Object handlerMapping = getHandlerMapping(req);
        if(handlerMapping == null){
            System.out.println("未匹配到handlerMapping");
            return;
        }
        //获得适配器
        HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);
        if(handlerAdapter == null){
            System.out.println("未匹配到handlerAdapter");
            return;
        }
        //执行具体的控制器逻辑
        Object result = handlerAdapter.handle(req,resp,handlerMapping);

        //将执行结果 输出到前段
        PrintWriter writer = resp.getWriter();
        writer.println(result);
        writer.flush();
        writer.close();
    }

    protected Object getHandlerMapping(HttpServletRequest request) {
        if (this.handlerMappings != null) {
            for (HandlerMapping mapping : this.handlerMappings) {
                Object handler = mapping.getHandlerMapping(request.getRequestURI());
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    protected HandlerAdapter getHandlerAdapter(Object handlerMapping) {
        if (this.handlerAdapters != null) {
            for (HandlerAdapter adapter : this.handlerAdapters) {
                boolean flag = adapter.supports(handlerMapping);
                if (flag) {
                    return adapter;
                }
            }
        }
        return null;
    }

}
