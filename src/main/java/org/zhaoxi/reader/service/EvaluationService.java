package org.zhaoxi.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.zhaoxi.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     * 禁用短评
     * @param evaluationId 短评id
     * @param disableReason 禁用理由
     * @return 短评对象
     */
    public Evaluation disable(Long evaluationId, String disableReason);
}
