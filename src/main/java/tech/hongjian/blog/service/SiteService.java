package tech.hongjian.blog.service;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.consts.Order;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.db.entity.Comment;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.model.Statistics;
import tech.hongjian.blog.utils.BlogUtils;
import tech.hongjian.blog.utils.Environment;

import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SiteService {
    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private MetaService metaService;

    @Autowired
    private CacheManager cache;


    public void initSite(User user, String siteTitle, String siteUrl) {
        String pwd = BlogUtils.encryptPassword(user.getUsername(), user.getPassword());
        user.setPassword(pwd);
        user.setScreenName(user.getUsername());
        user.setCreated(new Date());

        Integer uid = userService.save(user);

        // 修改系统属性，并写入配置文件
        BlogUtils.createLockFile();

        if (siteUrl.endsWith("/")) {
            siteUrl = siteUrl.substring(0, siteUrl.length() - 1);
        }
        if (!siteUrl.startsWith("http")) {
            siteUrl = "http://".concat(siteUrl);
        }

        optionService.saveOption("site_title", siteTitle);
        optionService.saveOption("site_url", siteUrl);
        optionService.saveOption(Types.ATTACH_URL, siteUrl + "/upload");
        BlogConsts.OPTIONS = Environment.of(optionService.getOptions());
    }

    public PageInfo<Comment> recentComments(int limit) {
        return commentService.selectPageOrderBy("created", Order.DESC, limit);
    }

    public PageInfo<Comment> recentComments(boolean approved, int limit) {
        return commentService.selectPageOrderBy(true, "created", Order.DESC, limit);
    }

    public List<Log> recentLogs(int limit) {
        return logService.selectOrderBy("created", Order.DESC, limit);
    }

    public List<Content> getContent(String type, int limit) {
        if (Types.RECENT_ARTICLE.equals(type)) {
            return contentService.selectByParams(Types.ARTICLE,
                    Types.PUBLISH, "id", Order.DESC, limit);
        }

        if (Types.RANDOM_ARTICLE.equals(type)) {
            return contentService.selectRandom(Types.ARTICLE, Types.PUBLISH, limit);
        }
        return Collections.emptyList();
    }

    public Statistics getStatistics() {
        Statistics statistics = cache.get(Types.C_STATISTICS);
        if (statistics != null) {
            return statistics;
        }
        statistics = new Statistics();
        long articles = contentService.countByParam(Types.ARTICLE, Types.PUBLISH);
        long pages = contentService.countByParam(Types.PAGE, Types.PUBLISH);
        long comments = commentService.count();
        long attach = attachService.count();
        long tags = metaService.count(Types.TAG);
        long categories = metaService.count(Types.CATEGORY);

        statistics.setArticles(articles);
        statistics.setPages(pages);
        statistics.setComments(comments);
        statistics.setAttachs(attach);
        statistics.setTags(tags);
        statistics.setCategories(categories);

        cache.set(Types.C_STATISTICS, statistics);
        return statistics;
    }

    public void clearStatistics() {
        cleanCache(Types.C_STATISTICS);
    }

    public void cleanCache(String key) {
        if ("*".equals(key)) {
            cache.clean();
        } else {
            cache.del(key);
        }
    }

    public Set<String> getBlackList() {
        String ips = optionService.getOptionValue(Types.BLOCK_IPS);
        if (StringUtils.isBlank(ips)) {
            return Collections.emptySet();
        }
        String[] ipArr = ips.split(",|，");
        Set<String> set = new HashSet<>(ipArr.length);
        for (String ip : ipArr) {
            if (StringUtils.isNotBlank(ip)) {
                set.add(StringUtils.trim(ip));
            }
        }
        return set;
    }


    public PageInfo<Content> recentArticles(int limit) {
        return contentService.selectPageByParams(Types.ARTICLE, Types.PUBLISH, null,
                "id", Order.DESC, 1, limit);
    }
}
