package tech.hongjian.blog.consts;

import java.util.Arrays;
import java.util.List;

public interface LogActions {
    String LOGIN = "登录后台";

    String UP_PWD = "修改密码";

    String UP_INFO = "修改个人信息";

    String DEL_ATTACH = "删除附件";

    String DEL_ARTICLE = "删除文章";

    String DEL_PAGE = "删除页面";

    String SYS_BACKUP = "系统备份";

    String SYS_SETTING = "保存系统设置";

    String THEME_SETTING = "主题设置";

    String INIT_SITE = "初始化站点";

    String RELOAD_SYS = "重启系统";

    String VISIT = "访问";

    static List<String> getAllActions() {
        return Arrays.asList(LOGIN, UP_PWD, UP_INFO, DEL_ATTACH, DEL_ARTICLE, DEL_PAGE,
                SYS_BACKUP, SYS_SETTING, THEME_SETTING, INIT_SITE, RELOAD_SYS, VISIT);
    }
}
