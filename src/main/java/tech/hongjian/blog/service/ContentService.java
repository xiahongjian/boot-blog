package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.mapper.ContentMapper;
import tech.hongjian.blog.frm.exception.ServiceException;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.Date;
import java.util.List;

@Service
public class ContentService {
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private SiteService siteService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private RelationshipService relationshipService;

    public int save(@NonNull Content content) {
        return contentMapper.insert(content);
    }

    public Content find(int id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    public Content getContent(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        if (StringUtils.isNumeric(id)) {
            return find(NumberUtils.toInt(id));
        }
        return findBySlug(id);
    }

    public Content findBySlug(String slug) {
        return contentMapper.selectBySlug(slug);
    }

    public List<Content> selectByParams(String type, String status,
                                        @NonNull String prop, @NonNull Order order,
                                        int count) {
        return PageHelper.startPage(1, BlogUtils.adjustPageSize(count), false)
                .doSelectPage(() -> contentMapper.selectByParams(type, status, null, null,
                        prop, order.name()));
    }

    public List<Content> selectByParams(String type, String status, Boolean allowFeed) {
        return contentMapper.selectByParams(type, status, null, allowFeed, "id", Order.DESC.name());
    }

    public PageInfo<Content> selectPageByParams(String type, String status,
                                        @NonNull String prop, @NonNull Order order,
                                        int page, int count) {
        return PageHelper.startPage(page, BlogUtils.adjustPageSize(count))
                .doSelectPageInfo(() -> contentMapper.selectByParams(type, status, null, null,
                        prop, order.name()));
    }

    public PageInfo<Content> selectPageByParams(String type, String status, String keyword,
                                                @NonNull String prop, @NonNull Order order,
                                                int page, int count) {
        return PageHelper.startPage(page, BlogUtils.adjustPageSize(count))
                .doSelectPageInfo(() -> contentMapper.selectByParams(type, status, keyword, null,
                        prop, order.name()));
    }

    public List<Content> selectByParams(String type, String status,
                                        @NonNull String prop, @NonNull Order order) {
        return contentMapper.selectByParams(type, status, null, null, prop, order.name());
    }


    public PageInfo<Content> selectByParams(String type, int pn, int count) {
        return PageHelper.startPage(pn, count).doSelectPageInfo(() -> contentMapper.selectByParams(type, null, null, null, "id", "desc"));
    }

    public List<Content> selectByParams(String type, String status, int count) {
        return selectByParams(type, status, "id", Order.ASC, count);
    }


    public List<Content> selectRandom(String type, String status, int count) {
        return PageHelper.startPage(1, BlogUtils.adjustPageSize(count), false).doSelectPage(() -> contentMapper.selectRandom(type, status));
    }

    public long countByParam(String type, String status) {
        return PageHelper.count(() -> contentMapper.selectByParams(type, status, null, null,
                "id", Order.ASC.name()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(int id) {
        if (find(id) == null) {
            throw new ServiceException("No such article whoes id is " + id);
        }
        contentMapper.deleteByPrimaryKey(id);
        relationshipService.deleteByCid(id);
    }

    public Content selectById(int id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer publish(@NonNull Content content) {
        if (StringUtils.isBlank(content.getTitle())) {
            throw new ServiceException("文章标题不能为空。");
        }
        if (StringUtils.isBlank(content.getContent())) {
            throw new ServiceException("文章能容不能为空。");
        }
        if (content.getContent().length() > BlogConsts.MAX_FILE_SIZE) {
            throw new ServiceException("文章最多可输入" + BlogConsts.MAX_FILE_SIZE + "个字符。");
        }
        if (content.getSlug() != null && content.getSlug().length() < 5) {
            throw new ServiceException("路径太短了。");
        }
        if (getContent(content.getSlug()) != null) {
            throw new ServiceException("路径已存在。");
        }
        Date now = new Date();
        if (content.getCreated() == null) {
            content.setCreated(now);
        }
        content.setModified(now);
        // 处理emoji
        content.setContent(EmojiParser.parseToAliases(content.getContent()));
        contentMapper.insert(content);
        siteService.clearStatistics();

        handleMates(content);

        return content.getId();
    }

    public Integer update(@NonNull Content content) {
        if (find(content.getId()) == null) {
            throw new ServiceException("更新的文章不存在，请重试。");
        }
        content.setModified(new Date());
        content.setContent(EmojiParser.parseToAliases(content.getContent()));
        contentMapper.updateByPrimaryKeySelective(content);

        handleMates(content);
        return content.getId();
    }

    private void handleMates(@NonNull Content content) {
        metaService.saveMates(content.getId(), content.getTags(), Types.TAG);
        metaService.saveMates(content.getId(), content.getCategories(), Types.CATEGORY);
    }


    public void visit(Integer id) {
        Content content = find(id);
        if (content != null) {
            content.setHits(BlogUtils.addOne(content.getHits()));
            update(content);
        }
    }

    public Content getNhContent(String type, Date created) {
        if (Types.PREV.equals(type)) {
            return contentMapper.selectPre(created);
        } else if (Types.NEXT.equals(type)) {
            return contentMapper.selectNext(created);
        }
        return null;
    }

    public PageInfo<Content> getArticles(Integer metaId, int page, int limit) {
        return PageHelper.startPage(page, limit).doSelectPageInfo(() -> contentMapper.selectArticles(metaId));
    }
}
