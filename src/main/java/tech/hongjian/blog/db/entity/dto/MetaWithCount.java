package tech.hongjian.blog.db.entity.dto;

import lombok.Data;
import tech.hongjian.blog.db.entity.Meta;

@Data
public class MetaWithCount extends Meta {
    private Long count;
}
