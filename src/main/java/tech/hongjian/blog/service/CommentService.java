package tech.hongjian.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.db.entity.Comment;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.biz.CommentWithChildren;
import tech.hongjian.blog.db.mapper.CommentMapper;
import tech.hongjian.blog.frm.exception.ServiceException;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ContentService contentService;


    public List<Comment> selectAllOrderBy(@NonNull String prop, @NonNull Order order) {
        return commentMapper.selectOrderBy(prop, order.name());
    }

    public List<Comment> selectAllOrderBy(boolean approved, @NonNull String prop,
                                          @NonNull Order order) {
        return commentMapper.selectApprovedOrderBy(approved, prop, order.name());
    }

    public List<Comment> selectOrderBy(@NonNull String prop, @NonNull Order order,
                                       int limit) {
        return PageHelper.startPage(1, BlogUtils.adjustPageSize(limit), false)
                .doSelectPage(() -> selectAllOrderBy(prop, order));
    }

    public PageInfo<Comment> selectPageOrderBy(@NonNull String prop,
                                               @NonNull Order order, int limit) {
        return PageHelper.startPage(1, limit).doSelectPageInfo(() -> selectAllOrderBy(prop, order));
    }

    public PageInfo<Comment> selectPageOrderBy(boolean approved, @NonNull String prop,
                                               @NonNull Order order, int limit) {
        return PageHelper.startPage(1, limit).doSelectPageInfo(() -> commentMapper.selectApprovedOrderBy(true, prop, order.name()));
    }

    public long count() {
        return PageHelper.count(() -> commentMapper.selectAll());
    }

    public PageInfo<Comment> notSelfComment(int page, int count, Integer uid) {
        return PageHelper.startPage(page, BlogUtils.adjustPageSize(count)).doSelectPageInfo(() -> commentMapper.selectNotSelf(uid));
    }

    public Comment selectById(Integer id) {
        return id == null ? null : commentMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id, Integer cid) {
        commentMapper.deleteByPrimaryKey(id);
        Content content = contentService.selectById(cid);
        if (content != null) {
            content.setCommentsNum(content.getCommentsNum() - 1);
        }
        contentService.update(content);
    }

    public void updateStatue(Integer id, String status) {
        Comment comment = selectById(id);
        if (comment == null) {
            throw new ServiceException("评论不存在");
        }
        comment.setStatus(status);
        update(comment);
    }

    public int update(Comment comment) {
        return commentMapper.updateByPrimaryKey(comment);
    }

    public int save(Comment comment) {
        return commentMapper.insert(comment);
    }

    public void saveComment(Comment comment) {
        if (comment.getParent() != null) {
            Comment parent = selectById(comment.getParent());
            if (parent == null) {
                throw new ServiceException("回复的评论不存在");
            }
        }
        Content content = contentService.selectById(comment.getCid());
        if (content == null) {
            throw new ServiceException("回复的文章不存在");
        }
        content.setCommentsNum(BlogUtils.addOne(content.getCommentsNum()));
        contentService.update(content);
        save(comment);
    }


    public PageInfo<CommentWithChildren> getComments(@NonNull Integer cid, Integer uid,
                                                     int page, int limit) {
        PageInfo<Comment> comments =
                PageHelper.startPage(page, limit).doSelectPageInfo(() -> commentMapper.getArticleCommentsByUser(cid, uid, null));


        List<CommentWithChildren> result = new ArrayList<>();
        comments.getList().forEach(c -> {
            CommentWithChildren child = new CommentWithChildren(c);
            List<Comment> children = new ArrayList<>();
            getChildren(children, cid, uid, c.getId());
            child.setChildren(children);
            child.setLevel(children.isEmpty() ? 0 : 1);
            result.add(child);
        });
        PageInfo<CommentWithChildren> pageInfo = new PageInfo<>(result);
        pageInfo.setPages(comments.getPages());
        pageInfo.setPageNum(comments.getPageNum());
        pageInfo.setHasNextPage(comments.isHasNextPage());
        pageInfo.setHasPreviousPage(comments.isHasPreviousPage());
        pageInfo.setNavigateFirstPage(comments.getNavigateFirstPage());
        pageInfo.setNavigateLastPage(comments.getNavigateLastPage());
        pageInfo.setNavigatepageNums(comments.getNavigatepageNums());
        pageInfo.setNavigatePages(comments.getNavigatePages());
        return pageInfo;
    }

    private void getChildren(List<Comment> children, Integer cid, Integer uid,
                             Integer parent) {
        List<Comment> comments = commentMapper.getArticleCommentsByUser(cid, uid, parent);
        comments.forEach(c -> {
            children.addAll(comments);
            comments.forEach(e -> {
                getChildren(children, cid, uid, parent);
            });
        });
    }
}
