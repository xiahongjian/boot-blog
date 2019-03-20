package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hongjian.blog.db.entity.Attach;
import tech.hongjian.blog.db.mapper.AttachMapper;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.List;

@Service
public class AttachService {
    @Autowired
    private AttachMapper attachMapper;
    @Autowired
    private SiteService siteService;

    public long count() {
        return PageHelper.count(() -> attachMapper.selectAll());
    }

    public PageInfo<Attach> select(int pn, int limit) {
        return PageHelper.startPage(pn, BlogUtils.adjustPageSize(limit)).doSelectPageInfo(() -> attachMapper.selectAll());
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(@NonNull List<Attach> attaches) {
        attaches.forEach(attach -> save(attach));
    }

    public void save(@NonNull Attach attach) {
        attachMapper.insert(attach);
    }

    public Attach selectById(Integer id) {
        if (id == null) {
            return null;
        }
        return attachMapper.selectByPrimaryKey(id);
    }

    public void delete(@NonNull Integer id) {
        attachMapper.deleteByPrimaryKey(id);
    }
}
