package com.vlad.store.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/webjars/**")
//                .addResourceLocations("/webjars/");
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("index.html");
////        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login.html");
////        registry.addViewController("/img/login").setViewName("img/login.jpg");
//    }
}
