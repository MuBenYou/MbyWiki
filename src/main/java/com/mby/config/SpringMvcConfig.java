package com.mby.config;

import com.mby.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LogInterceptor loginterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginterceptor)//添加拦截器
                .addPathPatterns("/**").excludePathPatterns("/logi");


    }
}
