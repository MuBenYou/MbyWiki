package com.mby.service;

import com.mby.domain.Ebook;
import com.mby.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service//注解 将这个Service交给Spring来管理
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<Ebook> list() {
        return  ebookMapper.selectByExample(null);//自动生成代码，selectByExample()查询所有字段
    }

}
