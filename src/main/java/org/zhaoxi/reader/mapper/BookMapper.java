package org.zhaoxi.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zhaoxi.reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {
    public void updateEvaluation();
}
