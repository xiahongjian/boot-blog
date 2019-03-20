package tech.hongjian.blog.db.entity;

import lombok.Data;

import javax.persistence.*;

@Data
public class Meta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String slug;

    private String type;

    private String description;

    private Integer sort;

    private Integer parent;

}