package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.hongjian.blog.db.entity.Relationship;
import tech.hongjian.blog.db.mapper.RelationshipMapper;

@Service
public class RelationshipService {
    @Autowired
    private RelationshipMapper relationshipMapper;

    public long count(Integer cid, Integer mid) {
        return PageHelper.count(() -> relationshipMapper.selectByParams(cid, mid));
    }

    public int save(Relationship relationship) {
        return relationshipMapper.insert(relationship);
    }

    public void deleteByCid(int id) {
        relationshipMapper.deleteByCid(id);
    }
}
