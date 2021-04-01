package org.zhaoxi.reader.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhaoxi.reader.mapper.TestMapper;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;

    @Transactional
    public void batchImport() {
        for(int i = 0; i < 5; i++) {
            testMapper.insert();
        }
    }
}
