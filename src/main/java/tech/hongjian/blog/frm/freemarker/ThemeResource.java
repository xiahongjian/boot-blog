package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@FMMethod("theme")
public class ThemeResource extends FMMethodBase {
    @Autowired
    private OptionService optionService;

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPath;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String resource = toStr(arguments.get(0));
        String theme = optionService.getOptionValue("site_theme");
        String contextPath = WebUtil.getRequest().getContextPath();
        return WebUtil.urlContact(contextPath, WebUtil.staticPattern2path(staticPath),
                "themes", theme, resource);
    }
}
