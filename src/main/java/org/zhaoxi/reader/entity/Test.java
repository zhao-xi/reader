package org.zhaoxi.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test") // 说明实体对应的数据库表明
public class Test {
    @TableId(type = IdType.AUTO) // 主键
    @TableField("id") // 说明变量对应的数据表字段名（字段名和属性名一致时可省略，满足驼峰命名也可省略）
    private Integer id;

    @TableField("content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
