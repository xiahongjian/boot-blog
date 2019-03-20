package tech.hongjian.blog.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    @Column(name = "home_url")
    private String homeUrl;

    @Column(name = "screen_name")
    private String screenName;

    private Date created;

    private Integer activated;

    private Integer logged;

    @Column(name = "group_name")
    private String groupName;

}