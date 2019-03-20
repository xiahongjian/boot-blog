package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.List;

@FMMethod("showContent")
public class ArticleContent extends FMMethodBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return BlogUtils.mdToHtml(toStr(arguments.get(0)));
    }
}
