package tech.hongjian.blog.controller;

import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.db.entity.Comment;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.dto.CommentWithChildren;
import tech.hongjian.blog.service.*;
import tech.hongjian.blog.utils.BlogUtils;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class IndexController extends BaseController {
    public static String ERROR_BASE_PATH = "/common";

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private CacheManager cache;

    @Autowired
    private OptionService optionService;

    @RequestMapping(BlogConsts.ERROR_URI + "/404")
    public String error404() {
        return ERROR_BASE_PATH + "/error_404";
    }

    @RequestMapping(BlogConsts.ERROR_URI + "/500")
    public String error500() {
        return ERROR_BASE_PATH + "/error_500";
    }


    /**
     * 首页
     */
    @GetMapping({"", "/"})
    public String index(Model model, @RequestParam(defaultValue = "12") int limit) {
        return index(model, 1, limit);
    }

    /**
     * 首页分页
     */
    @GetMapping(value = {"page/{page}"})
    public String index(Model model, @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "12") int limit) {
        page = page < 0 ? 1 : page;
        model.addAttribute("title", title(page));
        model.addAttribute("page_num", page);
        model.addAttribute("limit", limit);
        model.addAttribute("is_home", true);
        model.addAttribute("page_prefix", "/page");
        return render("index");
    }


    /**
     * 文章页
     */
    @GetMapping(value = {"article/{id}"})
    public String post(Model model, @PathVariable String id,
                       @RequestParam(defaultValue = "1") Integer cp) {
        Content content = contentService.getContent(id);
        if (content == null || !Types.PUBLISH.equals(content.getStatus()) || !Types.ARTICLE.equals(content.getType())) {
            return render404();
        }

        PageInfo<CommentWithChildren> comments =
                commentService.getComments(content.getId(), getUid(), cp, 10);

        model.addAttribute("article", content);
        model.addAttribute("is_post", true);
        model.addAttribute("commentsPage", comments);

        contentService.visit(content.getId());

        return render("post");
    }

    @ResponseBody
    @PostMapping(value = "comment")
    public RestResponse comment(Comment comment) {

        String val = WebUtil.getRealIp() + ":" + comment.getCid();
        Integer count = cache.hget(Types.COMMENTS_FREQUENCY, val);
        if (null != count && count > 0) {
            return RestResponse.fail("您发表评论太快了，请过会再试");
        }

        comment.setAuthor(EmojiParser.parseToAliases(comment.getAuthor()));
        comment.setContent(EmojiParser.parseToAliases(comment.getContent()));
        comment.setIp(WebUtil.getRealIp());
        comment.setParent(comment.getId());
        comment.setCreated(new Date());
        comment.setId(null);
        comment.setAuthorId(getUid());
        comment.setStatus(getUid() != null ? Types.CMT_STATUE.APPROVED : Types.CMT_STATUE.NOT_AUDIT);

        commentService.saveComment(comment);
        WebUtil.cookie("tale_remember_author", WebUtil.encode(comment.getAuthor()),
                7 * 24 * 60 * 60);
        WebUtil.cookie("tale_remember_mail", WebUtil.encode(comment.getMail()),
                7 * 24 * 60 * 60);
        if (StringUtils.isNotBlank(comment.getUrl())) {
            WebUtil.cookie("tale_remember_url", WebUtil.encode(comment.getUrl()),
                    7 * 24 * 60 * 60);
        }

        // 设置对每个文章30秒可以评论一次
        cache.hset(Types.COMMENTS_FREQUENCY, val, 1, 30);
        siteService.clearStatistics();

        return RestResponse.ok();
    }

    @GetMapping(value = {"search/{keyword}"})
    public String search(Model model, @PathVariable("keyword") String keyword,
                         @RequestParam(defaultValue = "12") int limit) {
        return search(model, keyword, 1, limit);
    }

    @GetMapping(value = {"search"})
    public String search(Model model, @RequestParam(defaultValue = "12") int limit) {
        String keyword = WebUtil.getRequest().getParameter("s");
        return search(model, keyword, 1, limit);
    }

    @GetMapping(value = {"search/{keyword}/{page}"})
    public String search(Model model, @PathVariable("keyword") String keyword,
                         @PathVariable("page") int page, @RequestParam(defaultValue =
            "12") int limit) {

        page = page < 0 ? 1 : page;

        PageInfo<Content> articles = contentService.selectPageByParams(Types.ARTICLE,
                Types.PUBLISH, keyword, "id", Order.DESC, page, limit);

        model.addAttribute("posts", articles);

        model.addAttribute("type", "搜索");
        model.addAttribute("keyword", keyword);
        model.addAttribute("page_prefix", "/search/" + keyword);
        return render("page-category");
    }

    @ResponseBody
    @GetMapping(value = {"feed", "feed.xml", "atom.xml"}, produces =
            MediaType.APPLICATION_XML_VALUE)
    public String feed() {
        List<Content> contents = contentService.selectByParams(Types.ARTICLE,
                Types.PUBLISH, true);


        try {
            String xml = BlogUtils.getRssXml(contents, optionService.getOptionValue(
                    "site_url", ""), optionService.getOptionValue("site_tile", ""),
                    optionService.getOptionValue("site_description", ""));
            return xml;
        } catch (Exception e) {
            log.error("生成 rss 失败", e);
        }
        return "";
    }

    @RequestMapping(value = "logout")
    public void logout() {
        WebUtil.logout();
    }

    private String title(int pn) {
        return "第" + String.valueOf(pn) + "页";
    }
}
