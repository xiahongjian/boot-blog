package tech.hongjian.blog.db.entity;

import lombok.Data;

import java.util.List;

@Data
public class Archive {
    private String dateStr;
    private List<Content> articles;
}
