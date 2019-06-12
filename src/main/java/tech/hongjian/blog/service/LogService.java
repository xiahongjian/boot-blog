package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.db.mapper.LogMapper;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public PageInfo<Log> selectByParams(String action, String keyword, Integer author, String ip, Date from, Date to, int pn, int size) {
        Map<String, Object> params = new HashMap<>(6);
        if (StringUtils.isNotBlank(action)) {
            params.put("action", action);
        }
        if (StringUtils.isNoneBlank(keyword)) {
            params.put("keyword", "%" + keyword + "%");
        }
        if (author != null) {
            params.put("author", author);
        }
        if (StringUtils.isNoneBlank(ip)) {
            params.put("ip", ip + "%");
        }
        if (from != null) {
            params.put("from", from);
        }
        if (to != null) {
            params.put("to", to);
        }
        return PageHelper.startPage(pn, size).doSelectPageInfo(() -> logMapper.selectByParams(params));
    }
}
