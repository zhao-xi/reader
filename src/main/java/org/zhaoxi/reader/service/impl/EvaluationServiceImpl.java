package org.zhaoxi.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zhaoxi.reader.entity.Book;
import org.zhaoxi.reader.entity.Evaluation;
import org.zhaoxi.reader.entity.Member;
import org.zhaoxi.reader.mapper.BookMapper;
import org.zhaoxi.reader.mapper.EvaluationMapper;
import org.zhaoxi.reader.mapper.MemberMapper;
import org.zhaoxi.reader.service.EvaluationService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;

    /**
     * 按图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("state", "enable");
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        for(Evaluation e : evaluationList) {
            Member member = memberMapper.selectById(e.getMemberId());
            Book book = bookMapper.selectById(e.getBookId());
            e.setMember(member);
            e.setBook(book);
        }
        return evaluationList;
    }

    public IPage<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<Evaluation>(page, rows);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        queryWrapper.orderByDesc("create_time");
        IPage<Evaluation> pageObject = evaluationMapper.selectPage(p, queryWrapper);
        for(Evaluation e : pageObject.getRecords()) {
            Member member = memberMapper.selectById(e.getMemberId());
            Book book = bookMapper.selectById(e.getBookId());
            e.setMember(member);
            e.setBook(book);
        }
        return pageObject;
    }

    /**
     * 禁用短评
     *
     * @param evaluationId  短评id
     * @param disableReason 禁用理由
     * @return 短评对象
     */
    public Evaluation disable(Long evaluationId, String disableReason) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setState("disable");
        evaluation.setDisableReason(disableReason);
        evaluation.setDisableTime(new Date());
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
