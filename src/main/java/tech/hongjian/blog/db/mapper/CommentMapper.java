package tech.hongjian.blog.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.hongjian.blog.db.entity.Comment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommentMapper extends Mapper<Comment> {

    @Select("select * from `Comment` order by ${param} ${order}")
    List<Comment> selectOrderBy(@Param("param") String param,
                                   @Param("order") String order);

    @Select("select * from `Comment` where author_id != #{uid}")
    List<Comment> selectNotSelf(Integer uid);


    List<Comment> getArticleComments(Integer cid, Integer parent);
}