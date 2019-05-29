package tech.hongjian.blog.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.hongjian.blog.db.entity.Archive;
import tech.hongjian.blog.db.entity.Content;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface ContentMapper extends Mapper<Content> {

    List<Content> selectByParams(@Param("type") String type,
                                 @Param("status") String status,
                                 @Param("keyword") String keyword,
                                 @Param("allowFeed") Boolean allowFeed,
                                 @Param("prop") String prop,
                                 @Param("order") String order);

    List<Content> selectRandom(@Param("type") String type,
                               @Param("status") String status);

    @Select("select * from `content` where slug = #{slug}")
    Content selectBySlug(String slug);

    @Select("select * from `content` where type = 'post' and status = 'publish' and " +
            "created < #{created} order by created desc limit 1")
    Content selectPre(Date created);


    @Select("select * from `content` where type='post' and status = 'publish' and " +
            "created > #{created} order by created asc limit 1")
    Content selectNext(Date created);

    List<Content> selectArticles(Integer metaId);

    List<Archive> selectArchives();

    List<Content> selectArticlesByCreated(Date from, Date to);
}