package org.zhaoxi.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zhaoxi.reader.entity.Evaluation;
import org.zhaoxi.reader.exception.BussinessException;
import org.zhaoxi.reader.service.EvaluationService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showIndex() {
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        Map result = new HashMap();
        try {
            IPage<Evaluation> pageObject = evaluationService.paging(page, limit);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("data", pageObject.getRecords());
            result.put("count", pageObject.getTotal());
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disable(Long evaluationId, String reason) {
        Map result = new HashMap();
        try {
            evaluationService.disable(evaluationId, reason);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }
}
