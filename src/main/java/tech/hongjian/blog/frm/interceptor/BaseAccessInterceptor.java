package tech.hongjian.blog.frm.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.frm.annotation.Interceptor;
import tech.hongjian.blog.service.SiteService;
import tech.hongjian.blog.service.UserService;
import tech.hongjian.blog.utils.BlogUtils;
import tech.hongjian.blog.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Slf4j
@Interceptor(path = "/**")
public class BaseAccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private SiteService siteService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        String ip = WebUtil.getRealIp();
        Set<String> blockIps = siteService.getBlackList();
        if (blockIps.contains(ip)) {
            WebUtil.writeToResponse("You are not allowed to access.");
            return false;
        }

        String uri = httpServletRequest.getRequestURI();

        if (uri.startsWith(BlogConsts.STATIC_URI) || uri.startsWith(BlogConsts.ERROR_URI)) {
            return true;
        }

        // 未安装并且访问的url不是安装url
        if (!BlogUtils.isInstalled() && !uri.startsWith(BlogConsts.INSTALL_URI)) {
            httpServletResponse.sendRedirect(BlogConsts.INSTALL_URI);
            return false;
        }

        if (!uri.startsWith(BlogConsts.ADMIN_URI)) {
            return true;
        }

        // 已经安装
        if (BlogUtils.isInstalled()) {
            return isRedirect(httpServletRequest, httpServletResponse);
        }

        return true;
    }

    private boolean isRedirect(HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
        // TODO FOR TEST
//        WebUtil.setTestUser("admin");

        String uri = request.getRequestURI();
        User user = WebUtil.getLoginUser();
        if (user != null)
            return true;


        if (uri.startsWith(BlogConsts.LOGIN_URI)) {
            return true;
        }


        Integer userId = WebUtil.getCookieUserId();
        if (userId != null) {
            user = userService.findById(userId);
            if (user != null)
                WebUtil.setLoginUser(user);
        }

        if (user == null) {
            response.sendRedirect(BlogConsts.LOGIN_URI);
            return false;
        }


        if (uri.startsWith(BlogConsts.ADMIN_URI) && !uri.startsWith(BlogConsts.LOGIN_URI)) {
            response.sendRedirect(BlogConsts.LOGIN_URI);
            return false;
        }

        return true;
    }
}
