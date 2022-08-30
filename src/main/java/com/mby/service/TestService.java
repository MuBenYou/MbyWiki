package com.mby.service;

import com.mby.domain.Test;
import com.mby.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service//注解 将这个Service交给Spring来管理
public class TestService {
    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return  testMapper.list();
    }

}
