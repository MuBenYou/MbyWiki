package com.mby.controller;

import com.mby.domain.Ebook;
import com.mby.service.EbookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//注解Controller返回一个界面
@RestController//返回字符串数据
@RequestMapping("/ebook")//访问ebook下的东西
public class EbookController {

    @Resource//
    private EbookService ebookService;


    @RequestMapping("/list")
    public List<Ebook>list(){
        return ebookService.list();
    }


}
