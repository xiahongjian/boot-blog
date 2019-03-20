package tech.hongjian.blog.db.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.hongjian.blog.db.entity.Relationship;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RelationshipMapper extends Mapper<Relationship> {
    @Select("select * from `relationship` where cid=#{cid} and mid=#{mid}")
    List<Relationship> selectByParams(@Param("cid") Integer cid, @Param("mid") Integer mid);

    @Delete("delete `relationship` where cid=#{id}")
    void deleteByCid(@Param("id") Integer id);
}