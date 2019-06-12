package tech.hongjian.blog.consts;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LogActions {
    LOGIN("登录后台"),

    UP_PWD("修改密码"),

    UP_INFO("修改个人信息"),

    DEL_ATTACH("删除附件"),

    DEL_ARTICLE("删除文章"),

    DEL_PAGE("删除页面"),

    SYS_BACKUP("系统备份"),

    SYS_SETTING("保存系统设置"),

    THEME_SETTING("主题设置"),

    INIT_SITE("初始化站点"),

    RELOAD_SYS("重启系统"),

    VISIT("访问");

    String value;

    LogActions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static List<String> getAllActions() {
        return Stream.of(values()).map(LogActions::name).collect(Collectors.toList());
    }
    public static Map<String, String> getActionMap() {
        return Stream.of(values()).collect(Collectors.toMap(LogActions::name, LogActions::getValue));
    }
}
