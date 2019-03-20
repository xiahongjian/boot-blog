package tech.hongjian.blog.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String action;

    private String data;

    @Column(name = "author_id")
    private Integer authorId;

    private String ip;

    private Date created;

    public Log(String action, String data, Integer authorId, String ip) {
        this.action = action;
        this.data = data;
        this.authorId = authorId;
        this.ip = ip;
        created = new Date();
    }
}