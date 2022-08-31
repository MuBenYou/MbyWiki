package com.mby.config;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*@ComponentScan({"com.mby","com.controller"})*///扫描以下全部包，交给Spring管理
@ComponentScan({"com.mby"})
@SpringBootApplication
@MapperScan("com.mby.mapper")//让springboot知道哪里是持久层=SQL
public class WikiApplication {

    private  static final Logger LOG= LoggerFactory.getLogger(WikiApplication.class);
    //LOG作用LoggerFactory.getLogger可以在IDE控制台打印日志,便于开发,一般加在代码最上面
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WikiApplication.class);
        //Environment 这个接口代表应用环境，它代表了两方面、一个是 profile 一个是 properties。
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");//LOg调用Info在控制台打印日志
        LOG.info("地址:\thttp://127.0.0.1:{}",env.getProperty("server.port"));//获取到配置文件的参数端口号


    }

}
