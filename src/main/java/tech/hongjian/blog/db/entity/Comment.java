package tech.hongjian.blog.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cid;

    private Date created;

    private String author;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "owner_id")
    private Integer ownerId;

    private String mail;

    private String url;

    private String ip;

    private String agent;

    private String type;

    private String status;

    private Integer parent;

    private String content;

}