package com.mby.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mby.domain.Ebook;
import com.mby.domain.EbookExample;
import com.mby.mapper.EbookMapper;
import com.mby.req.EbookQueryReq;
import com.mby.req.EbookSaveReq;
import com.mby.resp.EbookQueryResp;
import com.mby.resp.PageResp;
import com.mby.util.CopyUtil;
import com.mby.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service//注解 将这个Service交给Spring来管理
public class EbookService {
    private final static Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {

        EbookExample ebookExample = new EbookExample();//domain下的example mybatis给我们自动生成了很多方法New 处理 才能调用他的方法
        EbookExample.Criteria criteria=ebookExample.createCriteria();//Criteria相当于where条件， 把criteria创建出来

        if (!ObjectUtils.isEmpty(req.getName())){//不为空 才执行模糊查询 这样下面的查询selectBy里面是没有where条件的所有执行的selectByExample全部信息
            criteria.andNameLike("%"+req.getName()+"%");
        }
        // List<Ebook> ebooklist=ebookMapper.selectByExample(ebookExample);还有这个语句,这句话只对第1个遇到的select起作用。
        PageHelper.startPage(req.getPage(), req.getSize());//后端分页，第几（1）页，拿三条
        List<Ebook> ebooklist=ebookMapper.selectByExample(ebookExample);//自动生成代码，selectByExample()查询所有字段,selectByExample=基本查询语句，括号里面是查询条件
        //转换类型 ->list<Ebook> ->List<EbookQueryResp>

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooklist);
        LOG.info("总行数:{}",pageInfo.getTotal());//Total()前端定义的pagesize—个页面显示多少条数据
        LOG.info("总页数:{}",pageInfo.getPages());


        /*List<EbookQueryResp> respList =new ArrayList<>();//ArrayList的使用。* 存储字符串并遍历
        for (Ebook ebook : ebooklist){//foreach循环的意思 ebookList =>ebook
            EbookQueryResp ebookResp=new EbookQueryResp();//把响应的类型new出来，才能使用

            BeanUtils.copyProperties(ebook,ebookResp);//BeanUtils用于拷贝的方法，1.要拷贝哪个2.要拷贝到哪里去 用于将事件源的数据拷贝到目标源中
            respList.add(ebookResp);
        }*/
        List<EbookQueryResp> list = CopyUtil.copyList(ebooklist, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp=new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /*保存*/
    public void save(EbookSaveReq req){
        Ebook ebook = CopyUtil.copy(req,Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        }else {
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
