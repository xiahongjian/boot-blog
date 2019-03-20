package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.db.mapper.LogMapper;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;

    public Integer save(Log log) {
        return logMapper.insert(log);
    }


    public List<Log> selectOrderBy(@NonNull String prop, @NonNull Order order) {
        return logMapper.selectOrderBy(prop, order.name());
    }

    public List<Log> selectOrderBy(@NonNull String prop, @NonNull Order order, int count) {
        return PageHelper.startPage(1, BlogUtils.adjustPageSize(count)).doSelectPage(() -> selectOrderBy(prop, order));
    }
}
