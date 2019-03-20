package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.List;

@FMMethod("nextArticle")
public class NextArticleLink extends ArticleLinkBase {


    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Content content = contentParam(arguments);
        Content next = nextArticle(content);
        String tip = tipParam(arguments, "←");
        return articleLink(tip, next);
    }
}
