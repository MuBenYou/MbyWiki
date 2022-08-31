package com.mby.controller;

import com.mby.domain.Demo;
import com.mby.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//注解Controller返回一个界面
@RestController//返回字符串数据
@RequestMapping("/demo")//访问demo下的东西
public class DemoController {

    @Resource//
    private DemoService demoService;


    @RequestMapping("/list")
    public List<Demo>list(){
        return demoService.list();
    }


}
