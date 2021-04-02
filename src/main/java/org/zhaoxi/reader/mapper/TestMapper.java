package org.zhaoxi.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zhaoxi.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();
}
