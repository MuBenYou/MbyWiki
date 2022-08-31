package com.mby.service;

import com.mby.domain.Ebook;
import com.mby.domain.EbookExample;
import com.mby.mapper.EbookMapper;
import com.mby.req.EbookReq;
import com.mby.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service//注解 将这个Service交给Spring来管理
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();//domain下的example mybatis给我们自动生成了很多方法New 处理 才能调用他的方法
        EbookExample.Criteria criteria=ebookExample.createCriteria();//Criteria相当于where条件， 把criteria创建出来
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebooklist=ebookMapper.selectByExample(ebookExample);//自动生成代码，selectByExample()查询所有字段,selectByExample=基本查询语句，括号里面是查询条件

        List<EbookResp> respList =new ArrayList<>();
        for (Ebook ebook : ebooklist){//
            EbookResp ebookResp=new EbookResp();//把响应的类型new出来，才能使用
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }
        return  respList;
    }

}
