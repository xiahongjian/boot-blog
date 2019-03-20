package tech.hongjian.blog.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Statistics implements Serializable {
    // 文章数
    private long articles;
    // 页面数
    private long pages;
    // 评论数
    private long comments;
    // 分类数
    private long categories;
    // 标签数
    private long tags;
    // 附件数
    private long attachs;
}
