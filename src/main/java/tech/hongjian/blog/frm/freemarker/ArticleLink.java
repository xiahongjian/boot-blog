package tech.hongjian.blog.frm.freemarker;

import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@FMMethod("articleUrl")
public class ArticleLink extends FMMethodBase {
    @Autowired
    private OptionService optionService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Content content = (Content) ((StringModel) arguments.get(0)).getWrappedObject();
        String siteUrl = optionService.getOptionValue("site_url");
        return WebUtil.urlContact(siteUrl, "article",
                (StringUtils.isNotBlank(content.getSlug()) ?
                content.getSlug() : content.getId() + ""));
    }
}
