package tech.hongjian.blog.db.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tech.hongjian.blog.db.entity.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentWithChildren extends Comment {
    private int level;
    private List<Comment> children = new ArrayList<>();

    public CommentWithChildren(Comment comment) {
        this.setAuthor(comment.getAuthor());
        this.setAuthorId(comment.getAuthorId());
        this.setMail(comment.getMail());
        this.setId(comment.getId());
        this.setUrl(comment.getUrl());
        this.setCreated(comment.getCreated());
        this.setAgent(comment.getAgent());
        this.setIp(comment.getIp());
        this.setContent(comment.getContent());
        this.setOwnerId(comment.getOwnerId());
        this.setCid(comment.getCid());
    }
}
