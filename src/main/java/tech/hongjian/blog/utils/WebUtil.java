package tech.hongjian.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.db.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
public class WebUtil {
    public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String HEADER_PROXY_CLIENT_IP = "Proxy_Client_Ip";
    public static final String HEADER_WL_PROXY_CLIENT_IP = "WL_Proxy_Client_Ip";
    public static final String HEADER_HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HEADER_HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String UNKNOWN_HOST = "unknown";
    public static final String COOKIE_USR = "user";
    public static final String COOKIE_PWD = "password";



    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getRealIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader(HEADER_X_FORWARDED_FOR);

        if (!isCorrectIP(ip)) {
            ip = request.getHeader(HEADER_PROXY_CLIENT_IP);
        }

        if (!isCorrectIP(ip)) {
            ip = request.getHeader(HEADER_WL_PROXY_CLIENT_IP);
        }

        if (!isCorrectIP(ip)) {
            ip = request.getHeader(HEADER_HTTP_CLIENT_IP);
        }

        if (!isCorrectIP(ip)) {
            ip = request.getHeader(HEADER_HTTP_X_FORWARDED_FOR);
        }

        if (!isCorrectIP(ip)) {
            ip = request.getRemoteAddr();
        }

        // ip为多个，使用','分隔时，第一个为真实ip
        if (ip.indexOf(',') != -1) {
            ip = ip.split(",")[0];
        }

        return ip;
    }

    private static boolean isCorrectIP(String ip) {
        return StringUtils.isNotBlank(ip) && !UNKNOWN_HOST.equalsIgnoreCase(ip);
    }


    public static void writeToResponse(String content) {
        try {
            getResponse().getWriter().write(content);
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    public static void setTestUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setId(1);
        setLoginUser(user);
    }

    public static void logout() {
        HttpSession session = getRequest().getSession();
        session.removeAttribute(BlogConsts.LOGIN_SESSION_KEY);
        cookie(WebUtil.COOKIE_USR, "", 0);
        cookie(WebUtil.COOKIE_PWD, "", 0);
    }

    public static User getLoginUser() {
        HttpSession session = getRequest().getSession();
        return (User) session.getAttribute(BlogConsts.LOGIN_SESSION_KEY);
    }

    public static void setLoginUser(User user) {
        getRequest().getSession().setAttribute(BlogConsts.LOGIN_SESSION_KEY, user);
    }

    public static boolean isLogin() {
        return getLoginUser() != null;
    }

    public static String getCookieValue(String cookieName) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookieName, cookie.getName()))
                return cookie.getValue();
        }
        return null;
    }

    public static Integer getCookieUserId() {
        String str = getCookieValue(BlogConsts.COOKIE_USER_ID_NAME);
        return str == null ? null : Integer.valueOf(str);
    }

    public static Integer getUid() {
        return getLoginUser() == null ? null : getLoginUser().getId();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("x-requested-with");
        return "XMLHttpRequest".equals(header);
    }

    public static boolean isAjaxRequest() {
        return isAjaxRequest(getRequest());
    }

    public static String getClassPath() {
        return WebUtil.class.getResource("/").getPath();
    }

    public static String urlContact(String... pathes) {
        if (pathes == null || pathes.length == 0) {
            return "";
        }
        String url = StringUtils.join(pathes, "/");
        return url.replaceAll("(?<!:)/{2,}", "/");
    }

    public static String staticPattern2path(String patten) {
        return patten.replaceAll("/\\*\\*", "");
    }

    public static String genLink(String baseUrl, String c) {
        String url = WebUtil.urlContact(WebUtil.getRequest().getContextPath(), baseUrl,
                c);
        return "&nbsp;<a href=\"" + url + "\">" + c + "</a>&nbsp;";
    }


    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("Failed to encode string.", e);
        }
        return null;
    }

    public static void cookie(String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath("/");
        WebUtil.getResponse().addCookie(cookie);
    }
}
