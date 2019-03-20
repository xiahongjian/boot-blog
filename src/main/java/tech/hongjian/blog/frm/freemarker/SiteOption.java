package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.db.entity.Option;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.OptionService;

import java.util.List;

@FMMethod("siteOpt")
public class SiteOption extends FMMethodBase {
    @Autowired
    private OptionService optionService;
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments == null || arguments.size() < 1) {
            return null;
        }
        String name = toStr(arguments.get(0));
        Option option = optionService.find(name);
        return option == null ? null : option.getValue();
    }
}
