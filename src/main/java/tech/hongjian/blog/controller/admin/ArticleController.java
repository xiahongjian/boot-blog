package tech.hongjian.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.db.entity.Meta;
import tech.hongjian.blog.service.*;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/article")
public class ArticleController extends BaseController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private LogService logService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private OptionService optionService;

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "limit", defaultValue = "15") int limit) {

        PageInfo<Content> contents = contentService.selectByParams(Types.ARTICLE, page,
                limit);
        model.addAttribute("articles", contents);
        return "admin/article_list";
    }


    @PostMapping("delete")
    @ResponseBody
    public RestResponse delete(int id) {
        contentService.delete(id);
        siteService.clearStatistics();
        logService.save(new Log(LogActions.DEL_ARTICLE, "id:" + id, getUid(),
                WebUtil.getRealIp()));
        return RestResponse.ok();
    }


    @GetMapping("publish")
    public String newArticle(Model model) {
        List<Meta> categories = metaService.getMetas(Types.CATEGORY);
        model.addAttribute("categories", categories);
        model.addAttribute(Types.ATTACH_URL,
                optionService.getOptionValue(Types.ATTACH_URL));
        return "admin/article_edit";
    }

    @GetMapping("/{id}")
    public String editArticle(@PathVariable("id") Integer id, Model model) {
        Content content = contentService.selectById(id);
        if (content == null) {
            return render404();
        }
        model.addAttribute("content", content);

        List<Meta> categories = metaService.getMetas(Types.CATEGORY);
        model.addAttribute("categories", categories);
        model.addAttribute(Types.ATTACH_URL,
                optionService.getOptionValue(Types.ATTACH_URL));
        model.addAttribute("active", "article");
        return "admin/article_edit";
    }

    @ResponseBody
    @PostMapping("publish")
    public RestResponse publishArticle(Content content) {

        content.setType(Types.ARTICLE);
        content.setAuthorId(getUid());

        if (StringUtils.isBlank(content.getCategories())) {
            content.setCategories("默认分类");
        }

        Integer cid = contentService.publish(content);
        return RestResponse.ok(cid);
    }

    @ResponseBody
    @PostMapping(value = "modify")
    public RestResponse modifyArticle(Content content) {
        if (content == null || content.getId() == null) {
            return RestResponse.fail("缺少参数，请重试");
        }

        Integer id = content.getId();
        contentService.update(content);
        return RestResponse.ok(id);
    }

}
