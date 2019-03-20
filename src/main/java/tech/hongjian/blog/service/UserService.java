package tech.hongjian.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.db.mapper.UserMapper;
import tech.hongjian.blog.frm.exception.ServiceException;
import tech.hongjian.blog.utils.BlogUtils;
import tech.hongjian.blog.utils.WebUtil;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id) {
        return id == null ? null : userMapper.selectByPrimaryKey(id);
    }

    public Integer save(User user) {
        return userMapper.insert(user);
    }

    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public void login(String username, String password) {
        User user = findByUsername(username);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        if (!user.getPassword().equals(BlogUtils.encryptPassword(username, password))) {
            throw new ServiceException("密码不正确");
        }
        WebUtil.setLoginUser(user);
    }
}
