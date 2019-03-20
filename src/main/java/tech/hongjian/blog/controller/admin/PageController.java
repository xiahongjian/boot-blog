package tech.hongjian.blog.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.service.ContentService;
import tech.hongjian.blog.service.LogService;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("admin/page")
public class PageController extends BaseController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private LogService logService;

    @GetMapping("")
    public String index(Model model) {
        List<Content> pages = contentService.selectByParams(Types.PAGE, null, "id",
                Order.DESC);
        model.addAttribute("pages", pages);
        return "admin/page_list";
    }

    @GetMapping("new")
    public String newPage(Model model) {
        model.addAttribute(Types.ATTACH_URL,
                optionService.getOptionValue(Types.ATTACH_URL));
        return "admin/page_edit";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable int id, Model model) {
        Content content = contentService.selectById(id);
        if (content == null) {
            return render404();
        }
        model.addAttribute("content", content);
        model.addAttribute(Types.ATTACH_URL,
                optionService.getOptionValue(Types.ATTACH_URL));
        return "admin/page_edit";
    }

    @ResponseBody
    @PostMapping("publish")
    public RestResponse publishPage(Content content) {

        content.setType(Types.PAGE);
        content.setAllowPing(true);
        content.setAuthorId(getUid());
        contentService.publish(content);
        return RestResponse.ok();
    }

    @ResponseBody
    @PostMapping("modify")
    public RestResponse modifyArticle(Content content) {
        if (null == content || null == content.getId()) {
            return RestResponse.fail("缺少参数，请重试");
        }
        Integer id = content.getId();
        content.setType(Types.PAGE);
        contentService.update(content);
        return RestResponse.ok(id);
    }

    @ResponseBody
    @PostMapping("delete")
    public RestResponse delete(@RequestParam Integer id) {
        contentService.delete(id);
        logService.save(new Log(LogActions.DEL_PAGE, id + "", getUid(),
                WebUtil.getRealIp()));
        return RestResponse.ok();
    }
}
