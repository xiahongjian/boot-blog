package tech.hongjian.blog.controller;

import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.utils.WebUtil;

public abstract class BaseController {
    public static final String THEME = "themes/default";

    public String render(String viewName) {
        return THEME + "/" + viewName;
    }

    public String render404() {
        return "common/error_404";
    }

    public User user() {
        return WebUtil.getLoginUser();
    }

    public Integer getUid() {
        return user() == null ? null : user().getId();
    }
}
