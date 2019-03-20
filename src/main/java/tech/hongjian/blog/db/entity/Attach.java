package tech.hongjian.blog.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Attach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fname;

    private String ftype;

    private String fkey;

    @Column(name = "author_id")
    private Integer authorId;

    private Date created;

}