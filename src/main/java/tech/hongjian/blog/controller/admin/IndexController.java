package tech.hongjian.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Comment;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.model.Statistics;
import tech.hongjian.blog.service.*;
import tech.hongjian.blog.utils.JSONUtil;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController extends BaseController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private LogService logService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping({"", "/", "/index"})
    public String index(Model model) {

        PageInfo<Comment> comments = siteService.recentComments(5);
        List<Content> articles = siteService.getContent(Types.RECENT_ARTICLE, 5);
        List<Log> logs = siteService.recentLogs(20);
        Statistics statistics = siteService.getStatistics();


        model.addAttribute("comments", comments);
        model.addAttribute("articles", articles);
        model.addAttribute("logs", logs);
        model.addAttribute("statistics", statistics);
        model.addAttribute("site_url", optionService.getOptionValue("site_url"));
        return "admin/index";
    }

    @GetMapping("setting")
    public String setting(Model model) {
        Map<String, String> options = optionService.getOptions();
        model.addAttribute("options", options);
        return "admin/setting";
    }

    @ResponseBody
    @PostMapping("setting")
    public RestResponse saveSetting(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        paramMap.forEach((key, value) -> {
            if (value == null || value.length < 1) {
                return;
            }
            optionService.saveOption(key, value[0]);
        });
        logService.save(new Log(LogActions.SYS_SETTING, JSONUtil.toJson(paramMap),
                WebUtil.getUid(), WebUtil.getRealIp()));
        return RestResponse.ok();
    }

    @ResponseBody
    @PostMapping("advanced")
    public RestResponse saveAdvanced(
            @RequestParam("cache_key") String cacheKey,
                                     @RequestParam("block_ips") String blockIps,
                                     @RequestParam("plugin_name") String pluginName,
                                     @RequestParam("allow_install") String allowInstall) {
        cleanCache(cacheKey);
        optionService.saveOption(Types.BLOCK_IPS, blockIps);

        if (StringUtils.isNotBlank(allowInstall)) {
            optionService.saveOption(Types.ALLOW_INSTALL, allowInstall);
        }
        Map<String, String> params = new HashMap<>(3);
        params.put("block_ips", blockIps);
        params.put("plugin_name", pluginName);
        params.put("allow_install", allowInstall);
        logService.save(new Log(LogActions.SYS_SETTING, JSONUtil.toJson(params),
                WebUtil.getUid(), WebUtil.getRealIp()));
        return RestResponse.ok();
    }

    private void cleanCache(String cacheKey) {
        if ("*".equals(cacheKey)) {
            cacheManager.clean();
        } else {
            cacheManager.del(cacheKey);
        }
    }
}
