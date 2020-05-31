package com.example.sweater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

//        тут можно замеппить странички, не используя контроллер

//        пропускаем те, которые мы уже описали

//        нужна только страница авторизации
        registry.addViewController("/login").setViewName("login");

    }

}
