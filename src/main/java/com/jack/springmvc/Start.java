package com.jack.springmvc;

import com.jack.springmvc.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.jack")
public class Start {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        //初始化tomcat 各层容器
        Context appContext = tomcat.addWebapp("/", "C:\\project\\luban\\springmvc\\src\\main\\webapp");
        //打开IOC容器，扫描bean
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Start.class);

        tomcat.addServlet(appContext,"dispatcherServlet", new DispatcherServlet(ac));

        appContext.addServletMapping("/","dispatcherServlet");

        tomcat.start();
        tomcat.getServer().await();

    }
}
