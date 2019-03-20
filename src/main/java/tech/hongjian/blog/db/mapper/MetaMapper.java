package tech.hongjian.blog.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.hongjian.blog.db.entity.Meta;
import tech.hongjian.blog.db.entity.dto.MetaWithCount;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MetaMapper extends Mapper<Meta> {
    List<Meta> selectByType(@Param("type") String type);

    @Select("select * from `meta` where name=#{name} and type=#{type}")
    Meta selectByParams(@Param("name") String name, @Param("type") String type);

    List<MetaWithCount> selectByTypeWithCount(@Param("type") String type);
}