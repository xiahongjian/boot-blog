package tech.hongjian.blog.consts;

import tech.hongjian.blog.utils.Environment;

import java.util.Collections;

public class BlogConsts {

    public static final String LOGIN_SESSION_KEY = "login_user";
    public static final String COOKIE_USER_ID_NAME = "user_id";
    public static Environment OPTIONS = Environment.of(Collections.emptyMap());
    /**
     * 一页做多显示文章数量
     */
    public static final int MAX_PAGE_SIZE = 100;

    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 最大文章长度
     */
    public static final int MAX_TEXT_LENGTH = 200000;

    /**
     * 标题最大长度
     */
    public static final int MAX_TITLE_LENGTH = 200;

    /**
     * 上传文件最大20M
     */
    public static final int MAX_FILE_SIZE = 20480;


    public static final String STATIC_URI = "/static";

    public static final String ERROR_URI = "/error";

    public static final String INSTALL_URI = "/install";

    public static final String ADMIN_URI = "/admin";

    public static final String LOGIN_URI = "/admin/login";

    public static final String BLOG_CONFIG_FILE = "blog_config.json";

    public static final String MAPPER_PACKAGE = "tech.hongjian.blog.db.mapper";

}
