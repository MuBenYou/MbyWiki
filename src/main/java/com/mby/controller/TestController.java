package com.mby.controller;

import org.springframework.web.bind.annotation.*;

//注解Controller返回一个界面
@RestController//返回字符串数据
public class TestController {
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
        return  "Hello world";
    }
}
