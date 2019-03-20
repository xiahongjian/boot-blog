package tech.hongjian.blog.frm.freemarker;

import freemarker.ext.beans.StringModel;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.service.ContentService;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

public abstract class ArticleLinkBase extends FMMethodBase {
    @Autowired
    protected ContentService contentService;
    @Autowired
    protected OptionService optionService;


    protected Content contentParam(List arguments) {
        return (Content) ((StringModel) arguments.get(0)).getWrappedObject();
    }

    protected String tipParam(List arguments, String def) {
        if (arguments.size() < 2) {
            return def;
        }
        return toStr(arguments.get(1));
    }

    protected Content prevArticle(Content current) {
        return contentService.getNhContent(Types.PREV, current.getCreated());
    }

    protected Content nextArticle(Content current) {
        return contentService.getNhContent(Types.NEXT, current.getCreated());
    }

    protected String articleLink(String tip, Content content) {
        if (content == null) {
            return "";
        }
        String url = WebUtil.urlContact(optionService.getOptionValue("site_url"), "article", content.getId() + "");
        return "<a href=\"" + url + "\" >" + tip + "</a>";
    }

}
