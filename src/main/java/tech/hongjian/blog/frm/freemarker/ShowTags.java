package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@FMMethod("showTags")
public class ShowTags extends FMMethodBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.isEmpty()) {
            return "";
        }
        String tags = toStr(arguments.get(0));
        StringBuilder buf = new StringBuilder();
        for (String c : StringUtils.split(tags, ",")) {
            buf.append(WebUtil.genLink("tag", c));
        }
        return buf.toString();
    }
}
