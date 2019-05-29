package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@FMMethod("siteUrl")
public class SiteUrl extends FMMethodBase {
    @Autowired
    private OptionService optionService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        String siteUrl = optionService.getOptionValue("site_url");
        if (arguments != null && arguments.size() > 0) {
            String url = siteUrl;
            for (Object obj : arguments) {
                String path = toStr(obj);
                url = WebUtil.urlContact(url, path);
            }
            return url;
        }
        return siteUrl;
    }
}
