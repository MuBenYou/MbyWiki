package com.mby.controller;

import com.mby.req.EbookQueryReq;
import com.mby.req.EbookSaveReq;
import com.mby.resp.CommonResp;
import com.mby.resp.EbookQueryResp;
import com.mby.resp.PageResp;
import com.mby.service.EbookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//注解Controller返回一个界面
@RestController//返回字符串数据
@RequestMapping("/ebook")//访问ebook下的东西
public class EbookController {

    @Resource//
    private EbookService ebookService;


    @RequestMapping("/list")//当所有访问类型 返回list集合，类型为Ebook自己写的demain，根据数据库里的字段 teturn 调用注入你写的名字EbookService的list方法
    public CommonResp list(EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp=new CommonResp<>();//new Com 返回的参数类型是List集合的Ebook这个实体类的各种数据类型
        // CommonResp<>这里的范型是实际返回业务数据的类型，即content的类型，
        PageResp<EbookQueryResp> list =ebookService.list(req);//调用ebookService的List方法 返回给list这个变量 他的类型是List集合Ebook
        resp.setContent(list);//Com下定义的方法 get set方法的set 把你数据库拿到的参数设置进来 他的类型就是你自定义的List<Ebook>
        return resp;//最后返回resp
    }
    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req){
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }


}
