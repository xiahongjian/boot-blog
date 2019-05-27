package tech.hongjian.blog.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.hongjian.blog.db.entity.Log;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface LogMapper extends Mapper<Log> {

    @Select("select * from Log order by ${prop} ${order}")
    List<Log> selectOrderBy(@Param("prop") String prop, @Param("order") String order);

    List<Log> selectByParams(Map<String, Object> params);
}