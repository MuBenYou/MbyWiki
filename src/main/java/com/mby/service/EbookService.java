package com.mby.service;

import com.mby.domain.Ebook;
import com.mby.domain.EbookExample;
import com.mby.mapper.EbookMapper;
import com.mby.req.EbookReq;
import com.mby.resp.EbookResp;
import com.mby.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service//注解 将这个Service交给Spring来管理
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();//domain下的example mybatis给我们自动生成了很多方法New 处理 才能调用他的方法
        EbookExample.Criteria criteria=ebookExample.createCriteria();//Criteria相当于where条件， 把criteria创建出来
        if (!ObjectUtils.isEmpty(req.getName())){//不为空 才执行模糊查询 这样下面的查询selectBy里面是没有where条件的所有执行的selectByExample全部信息

            criteria.andNameLike("%"+req.getName()+"%");
        }

        List<Ebook> ebooklist=ebookMapper.selectByExample(ebookExample);//自动生成代码，selectByExample()查询所有字段,selectByExample=基本查询语句，括号里面是查询条件
        //转换类型 ->list<Ebook> ->List<EbookResp>
        /*List<EbookResp> respList =new ArrayList<>();//ArrayList的使用。* 存储字符串并遍历
        for (Ebook ebook : ebooklist){//foreach循环的意思 ebookList =>ebook
            EbookResp ebookResp=new EbookResp();//把响应的类型new出来，才能使用

            BeanUtils.copyProperties(ebook,ebookResp);//BeanUtils用于拷贝的方法，1.要拷贝哪个2.要拷贝到哪里去 用于将事件源的数据拷贝到目标源中
            respList.add(ebookResp);
        }*/
        //下面这句就是把上面注释的方法，封装起来用，封装类是CopyUtil
        //下面这句，列表复用
       List<EbookResp> list = CopyUtil.copyList(ebooklist,EbookResp.class);
        return  list;
    }

}
