package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.OptionService;

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
                concat(siteUrl, path);
            }
            return url;
        }
        return siteUrl;
    }

    private String concat(String url, String path) {
        if (StringUtils.isBlank(url)) {
            return path;
        }
        if (StringUtils.isBlank(path)) {
            return url;
        }
        return url + (url.endsWith("/") & !path.startsWith("/") ? "" : "/") + path;
    }
}
