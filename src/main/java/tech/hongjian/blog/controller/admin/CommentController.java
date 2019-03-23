package tech.hongjian.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Comment;
import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.service.CommentService;
import tech.hongjian.blog.service.SiteService;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

@Controller
@RequestMapping("admin/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private SiteService siteService;

    @GetMapping("")
    public String index(Model model, @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "15") int limit) {

        PageInfo<Comment> commentPage = commentService.notSelfComment(page, limit,
                getUid());

        model.addAttribute("comments", commentPage);
        return "admin/comment_list";
    }

    @ResponseBody
    @PostMapping(value = "delete")
    public RestResponse delete(Integer coid) {
        Comment comment = commentService.selectById(coid);
        if (comment == null) {
            return RestResponse.fail("不存在该评论");
        }
        commentService.delete(comment.getId(), comment.getCid());
        siteService.clearStatistics();
        return RestResponse.ok();
    }

    @ResponseBody
    @PostMapping(value = "")
    public RestResponse reply(Integer coid, String content) {

        if (null == coid || StringUtils.isBlank(content)) {
            return RestResponse.fail("请输入完整后评论");
        }

        if (content.length() > 2000) {
            return RestResponse.fail("请输入2000个字符以内的回复");
        }
        Comment c = commentService.selectById(coid);
        if (null == c) {
            return RestResponse.fail("不存在该评论");
        }
        content = EmojiParser.parseToAliases(content);

        User user = user();
        Comment comment = new Comment();
        comment.setAuthor(user.getUsername());
        comment.setAuthorId(user.getId());
        comment.setCid(c.getCid());
        comment.setIp(WebUtil.getRealIp());
        comment.setUrl(user.getHomeUrl());
        comment.setContent(content);
        comment.setMail(user.getEmail());
        comment.setParent(coid);
        commentService.saveComment(comment);
        siteService.clearStatistics();
        return RestResponse.ok();
    }

    @ResponseBody
    @PostMapping(value = "status")
    public RestResponse changeStatues(Integer coid, String status) {
        commentService.updateStatue(coid, status);
        return RestResponse.ok();
    }
}
