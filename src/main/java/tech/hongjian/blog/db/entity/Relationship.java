package tech.hongjian.blog.db.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Relationship {
    @Id
    private Integer cid;

    @Id
    private Integer mid;

}