package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.List;

@FMMethod("isBlank")
public class StrIsBlank extends FMMethodBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return StringUtils.isBlank(toStr(arguments.get(0)));
    }
}
