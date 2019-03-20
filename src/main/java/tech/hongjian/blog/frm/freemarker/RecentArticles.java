package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.SiteService;

import java.util.List;

@FMMethod("articles")
public class RecentArticles extends FMMethodBase {
    @Autowired
    SiteService siteService;
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Integer limit = toInt(arguments.get(0));
        return siteService.recentArticles(limit);
    }
}
