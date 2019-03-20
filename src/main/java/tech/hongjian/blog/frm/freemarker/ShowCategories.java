package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@FMMethod("showCategories")
public class ShowCategories extends FMMethodBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.isEmpty()) {
            return WebUtil.genLink("category", "默认分类");
        }
        String categoriesStr = toStr(arguments.get(0));
        StringBuilder buf = new StringBuilder();
        for (String c : StringUtils.split(categoriesStr, ",")) {
            buf.append(WebUtil.genLink("category", c));
        }
        return buf.toString();
    }



}
