package com.mby.service;

import com.mby.domain.Demo;
import com.mby.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service//注解 将这个Service交给Spring来管理
public class DemoService {
    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return  demoMapper.selectByExample(null);//自动生成代码，selectByExample()查询所有字段
    }

}
