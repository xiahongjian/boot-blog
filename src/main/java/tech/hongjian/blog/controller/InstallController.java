package tech.hongjian.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.service.LogService;
import tech.hongjian.blog.service.SiteService;
import tech.hongjian.blog.utils.BlogUtils;
import tech.hongjian.blog.utils.JSONUtil;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/install")
public class InstallController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private LogService logService;

    @GetMapping({"", "/"})
    public String install(Model model) {
        model.addAttribute("isInstalled", BlogUtils.isInstalled());
        return "install";
    }

    @ResponseBody
    @PostMapping({"", "/"})
    public RestResponse doInstall(HttpServletRequest request,
                                  @RequestParam("site_title") String siteTitle,
                                  @RequestParam("site_url") String siteUrl,
                                  @RequestParam("admin_user") String adminUser,
                                  @RequestParam("admin_pwd") String adminPwd,
                                  @RequestParam("admin_email") String adminEmail) {
        if (StringUtils.isBlank(siteTitle)
                || StringUtils.isBlank(siteUrl)
                || StringUtils.isBlank(adminUser)
                || StringUtils.isBlank(adminPwd)
                || StringUtils.isBlank(adminEmail)) {
            return RestResponse.fail("请确认网站信息输入完整");
        }

        if (adminPwd.length() < 6 || adminPwd.length() > 14) {
            return RestResponse.fail("请输入6-14位密码");
        }

        if (!BlogUtils.isEmail(adminEmail)) {
            return RestResponse.fail("邮箱格式不正确");
        }

        try {
            User user = new User();
            user.setUsername(adminUser);
            user.setEmail(adminEmail);
            user.setPassword(adminPwd);
            user.setGroupName(Types.GROUPS.ADMIN);
            siteService.initSite(user, siteTitle, siteUrl);
        } catch (Exception e) {
            log.warn("Failed to install.", e);
            return RestResponse.fail("安装失败");
        }
        logService.save(new Log(LogActions.INIT_SITE,
                JSONUtil.toJson(request.getParameterMap()), null, WebUtil.getRealIp()));
        return RestResponse.ok();
    }
}
