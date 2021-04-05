package org.zhaoxi.reader.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zhaoxi.reader.service.BookService;

import javax.annotation.Resource;

@Component
public class ComputeTask {
    @Resource
    private BookService bookService;

    // 定时任务（任务调度）
    @Scheduled(cron = "0 * * * * ?") // 每分钟一次
    public void updateEvaluation() {
        bookService.updateEvaluation();
        System.out.println("已更新所有图书评分");
    }
}
