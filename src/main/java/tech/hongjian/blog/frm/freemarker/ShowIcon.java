package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.List;

@FMMethod("showIcon")
public class ShowIcon extends FMMethodBase {
    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note",
            "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link",
            "bg-ico-design", "bg-ico-lock"};

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.isEmpty()) {
            return ICONS[0];
        }
        Integer id = toInt(arguments.get(0));
        return ICONS[id % ICONS.length];
    }
}
