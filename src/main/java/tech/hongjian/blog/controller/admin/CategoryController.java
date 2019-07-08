package tech.hongjian.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.biz.MetaWithCount;
import tech.hongjian.blog.service.MetaService;
import tech.hongjian.blog.service.SiteService;
import tech.hongjian.blog.utils.RestResponse;

import java.util.List;

@Controller("adminCategoryController")
@RequestMapping("admin/category")
public class CategoryController extends BaseController {
    @Autowired
    private MetaService metaService;

    @Autowired
    private SiteService siteService;

    @GetMapping("")
    public String index(Model model) {
        List<MetaWithCount> categories = metaService.getMatesWithCount(Types.CATEGORY);
        List<MetaWithCount> tags = metaService.getMatesWithCount(Types.TAG);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tags);
        return "admin/category";
    }

    @ResponseBody
    @PostMapping(value = "save")
    public RestResponse saveCategory(String cname, Integer mid) {
        metaService.saveMeta(Types.CATEGORY, cname, mid);
        siteService.cleanCache(Types.C_STATISTICS);
        return RestResponse.ok();
    }

    @ResponseBody
    @PostMapping(value = "delete")
    public RestResponse delete(int mid) {
        metaService.delete(mid);
        siteService.cleanCache(Types.C_STATISTICS);
        return RestResponse.ok();
    }
}
