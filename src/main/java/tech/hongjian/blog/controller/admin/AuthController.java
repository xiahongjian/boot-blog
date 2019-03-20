package tech.hongjian.blog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.frm.exception.ServiceException;
import tech.hongjian.blog.service.CacheManager;
import tech.hongjian.blog.service.LogService;
import tech.hongjian.blog.service.UserService;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

@Controller
@RequestMapping("/admin")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private CacheManager cache;

    @GetMapping("login")
    public String login() {
        return "/admin/login";
    }

    @PostMapping("login")
    @ResponseBody
    public RestResponse doLogin(String username, String password, String rememberMe) {
        String ip = WebUtil.getRealIp();
        Integer count = cache.hget("login_error_count", ip);
        if (count != null && count >= 3) {
            return RestResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
        }
        try {
            userService.login(username, password);
        } catch (ServiceException e) {
            count = count == null ? 1 : count++;
            cache.hset("login_error_count", ip, count, 10 * 60);
            logService.save(new Log(LogActions.LOGIN, e.getMessage(), null,
                    WebUtil.getRealIp()));
            return RestResponse.fail(e.getMessage());
        }
        logService.save(new Log(LogActions.LOGIN, "ok", WebUtil.getUid(),
                WebUtil.getRealIp()));
        cache.hdel("login_error_count", ip);
        return RestResponse.ok();
    }
}
