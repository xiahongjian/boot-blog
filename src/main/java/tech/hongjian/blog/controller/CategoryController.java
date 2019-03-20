package tech.hongjian.blog.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.Meta;
import tech.hongjian.blog.service.ContentService;
import tech.hongjian.blog.service.MetaService;

@Controller
public class CategoryController extends BaseController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private MetaService metaService;

    /**
     * 某个分类详情页
     */
    @GetMapping(value = {"category/{keyword}"})
    public String categories(Model model, @PathVariable String keyword,
                             @RequestParam(defaultValue = "12") int limit) {
        return this.categories(model, keyword, 1, limit);
    }

    /**
     * 某个分类详情页分页
     */
    @GetMapping(value = {"category/{keyword}/{page}"})
    public String categories(Model model, @PathVariable("keyword") String keyword,
                             @PathVariable("page") int page,
                             @RequestParam(defaultValue = "12") int limit) {
        page = page < 0 ? 1 : page;
        Meta metaDto = metaService.getMeta(keyword, Types.CATEGORY);
        if (null == metaDto) {
            return render404();
        }

        PageInfo<Content> contentsPage = contentService.getArticles(metaDto.getId(),
                page, limit);
        model.addAttribute("posts", contentsPage);
        model.addAttribute("meta", metaDto);
        model.addAttribute("type", "分类");
        model.addAttribute("keyword", keyword);
        model.addAttribute("is_category", true);
        model.addAttribute("page_prefix", "/category/" + keyword);

        return this.render("page-category");
    }


    @GetMapping(value = {"tag/{name}"})
    public String tagPage(Model model, @PathVariable String name,
                          @RequestParam(defaultValue = "12") int limit) {
        return this.tags(model, name, 1, limit);
    }

    /**
     * 标签下文章分页
     */
    @GetMapping(value = {"tag/{name}/{page}"})
    public String tags(Model model, @PathVariable("name") String name, @PathVariable(
            "page") int page, @RequestParam(defaultValue = "12") int limit) {
        page = page < 0 ? 1 : page;
        Meta metaDto = metaService.getMeta(name, Types.TAG);
        if (null == metaDto) {
            return this.render404();
        }

        PageInfo<Content> contentsPage = contentService.getArticles(metaDto.getId(),
                page, limit);
        model.addAttribute("posts", contentsPage);
        model.addAttribute("meta", metaDto);
        model.addAttribute("type", "标签");
        model.addAttribute("keyword", name);
        model.addAttribute("is_tag", true);
        model.addAttribute("page_prefix", "/tag/" + name);

        return this.render("page-category");
    }
}
