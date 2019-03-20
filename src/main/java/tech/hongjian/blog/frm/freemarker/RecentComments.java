package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.SiteService;

import java.util.List;

@FMMethod("comments")
public class RecentComments extends FMMethodBase {
    @Autowired
    private SiteService siteService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Integer limit = toInt(arguments.get(0));
        return siteService.recentComments(limit);
    }
}
