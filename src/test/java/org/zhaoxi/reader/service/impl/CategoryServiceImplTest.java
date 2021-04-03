package org.zhaoxi.reader.service.impl;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zhaoxi.reader.entity.Category;
import org.zhaoxi.reader.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CategoryServiceImplTest extends TestCase {
    @Resource
    private CategoryService categoryService;

    @Test
    public void testSelectAll() {
        List<Category> list = categoryService.selectAll();
        System.out.println(list);
    }
}