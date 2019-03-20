package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.List;

@FMMethod("prevArticle")
public class PrevArticleLink extends ArticleLinkBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Content content = contentParam(arguments);
        Content prev = prevArticle(content);
        String tip = tipParam(arguments, "→");
        return articleLink(tip, prev);
    }
}
