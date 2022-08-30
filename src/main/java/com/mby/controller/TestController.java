package com.mby.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//注解Controller返回一个界面
@RestController//返回字符串数据
public class TestController {
    @Value("${test.hello:TEst}")
    private String testHello;//获取配置的自定义的参数
    /*
    * GET获取，POST设置，PUT，DELETE删除
    * /user?id=1
    * /user/1
    * @return
    * */
    /*@GetMapping("/hello")
    @PostMapping
    @PutMapping
    @DeleteMapping
    @RequestMapping(value = "/user/1",method = RequestMethod.GET)
    @RequestMapping(value = "/user/1",method = RequestMethod.DELETE)*/
    @GetMapping("hello")//路径http://127.0.0.1:8080/hello
    public String hello(){
        return  "Hello world"+testHello;
    }

    @PostMapping("hello/post")//路径http://127.0.0.1:8080/hello
    public String helloPost(String name){
        return  "Hello world!Post,"+name;
    }
}
