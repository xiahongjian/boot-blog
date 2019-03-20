package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hongjian.blog.db.entity.Meta;
import tech.hongjian.blog.db.entity.Relationship;
import tech.hongjian.blog.db.entity.dto.MetaWithCount;
import tech.hongjian.blog.db.mapper.MetaMapper;
import tech.hongjian.blog.frm.exception.ServiceException;

import java.util.List;

@Service
public class MetaService {
    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private RelationshipService relationshipService;

    public long count(String type) {
        return PageHelper.count(() -> metaMapper.selectByType(type));
    }

    public int save(Meta meta) {
        return metaMapper.insert(meta);
    }

    public List<Meta> getMetas(String type) {
        return metaMapper.selectByType(type);
    }

    public List<MetaWithCount> getMatesWithCount(String type) {
        return metaMapper.selectByTypeWithCount(type);
    }

    public Meta getMeta(String name, String type) {
        return metaMapper.selectByParams(name, type);
    }

    public Meta findById(Integer id) {
        return id == null ? null : metaMapper.selectByPrimaryKey(id);
    }

    public int update(Meta meta) {
        return metaMapper.updateByPrimaryKey(meta);
    }

    public void saveMates(Integer id, String name, String type) {
        if (id == null) {
            throw new ServiceException("关联的ID不能为空");
        }
        if (StringUtils.isBlank(name)) {
            return;
        }
        String[] items = StringUtils.split(name, ",");
        for (String item : items) {
            if (StringUtils.isNotBlank(item)) {
                saveOrUpdate(id, item, type);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Integer id, String item, String type) {
        Meta meta = getMeta(item, type);
        Integer mid;
        if (meta != null) {
            mid = meta.getId();
        } else {
            meta = new Meta();
            meta.setName(item);
            meta.setType(type);
            meta.setSlug(item);
            save(meta);
            mid = meta.getId();
        }
        long count = relationshipService.count(id, mid);
        if (count == 0) {
            Relationship relationship = new Relationship();
            relationship.setCid(id);
            relationship.setMid(mid);
            relationshipService.save(relationship);
        }
    }

    public void saveMeta(String type, String name, Integer mid) {
        Meta meta = findById(mid);
        if (meta != null) {
            meta.setName(name);
            meta.setSlug(name);
            meta.setType(type);
            update(meta);
            return;
        }
        meta = getMeta(name, type);
        if (meta != null) {
            throw new ServiceException("此项目已存在");
        }
        meta = new Meta();
        meta.setType(type);
        meta.setName(name);
        meta.setSlug(name);
        save(meta);
    }

    public int delete(int mid) {
        if (findById(mid) == null) {
            throw new ServiceException("此项目不存在");
        }
        return metaMapper.deleteByPrimaryKey(mid);
    }
}
