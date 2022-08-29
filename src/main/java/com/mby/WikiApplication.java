package com.mby;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WikiApplication {
//打印日志
    private  static final Logger LOG= LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(WikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址:\thttp://127.0.0.1:{}",env.getProperty("server.port"));


    }

}
