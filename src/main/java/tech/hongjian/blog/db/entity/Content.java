package tech.hongjian.blog.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String slug;

    @Column(name = "thumb_img")
    private String thumbImg;

    private Date created;

    private Date modified;

    @Column(name = "author_id")
    private Integer authorId;

    private String type;

    private String status;

    @Column(name = "fmt_type")
    private String fmtType;

    private String tags;

    private String categories;

    private Integer hits;

    @Column(name = "comments_num")
    private Integer commentsNum;

    @Column(name = "allow_comment")
    private Boolean allowComment;

    @Column(name = "allow_ping")
    private Boolean allowPing;

    @Column(name = "allow_feed")
    private Boolean allowFeed;

    private String content;

}