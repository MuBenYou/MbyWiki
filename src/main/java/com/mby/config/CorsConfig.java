package com.mby.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//用来出来跨域问题
@Configuration
public class CorsConfig implements WebMvcConfigurer {//继承WebMvcConfigurere专门来解决跨域问题

    @Override
    public void addCorsMappings(CorsRegistry registry) {//CorsRegistry跨域资源共享登记。是spring中解决跨域的一种方式
        registry.addMapping("/**")//针对所有的请求地 比如我们的ebook
                .allowedOriginPatterns("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)//允许使用get post put等
                .allowCredentials(true)//运行前端携带的cokkie信息
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }

}
