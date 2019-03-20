package tech.hongjian.blog.frm.freemarker;

import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import tech.hongjian.blog.db.entity.Content;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.utils.WebUtil;

import java.util.List;

@FMMethod("showThumb")
public class ShowThumb extends FMMethodBase {
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPath;
    @Autowired
    private OptionService optionService;


    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Content content = (Content) ((StringModel) arguments.get(0)).getWrappedObject();
        String thumb = content.getThumbImg();
        if (StringUtils.isNotBlank(thumb)) {
            return thumb;
        }
        int index = content.getId() % 19 + 1;
        String theme = optionService.getOptionValue("site_theme");
        return WebUtil.urlContact(WebUtil.staticPattern2path(staticPath), "themes", theme, "img/rand", index + ".jpg");
    }
}
