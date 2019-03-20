package tech.hongjian.blog.db.mapper;

import org.apache.ibatis.annotations.Select;
import tech.hongjian.blog.db.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

    @Select("select * from User where username = #{username} limit 0, 1")
    User selectByUsername(String username);
}