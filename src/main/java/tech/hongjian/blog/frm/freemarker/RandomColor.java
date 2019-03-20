package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.RandomUtils;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.List;

@FMMethod("randomColor")
public class RandomColor extends FMMethodBase {
    private static final String[] COLORS = {"default", "primary", "success", "info",
            "warning", "danger", "inverse", "purple", "pink"};

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return COLORS[RandomUtils.nextInt(0, COLORS.length)];
    }
}
