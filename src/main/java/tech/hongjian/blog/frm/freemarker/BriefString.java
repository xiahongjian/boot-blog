package tech.hongjian.blog.frm.freemarker;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.utils.BlogUtils;

import java.util.List;

@FMMethod("brief")
public class BriefString extends FMMethodBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String str = toStr((SimpleScalar) arguments.get(0));
        int len = toInt((SimpleNumber) arguments.get(1)) ;
        if (arguments.size() >= 3) {
            TemplateBooleanModel isArticle = (TemplateBooleanModel) arguments.get(2);
            if (isArticle.getAsBoolean()) {
                str = BlogUtils.htmlToText(BlogUtils.mdToHtml(str));
            }
        }
        if (str.length() + 3 <= len) {
            return str;
        }
        return str.substring(0, len) + "...";
    }
}
