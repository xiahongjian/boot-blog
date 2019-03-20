package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.RandomUtils;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.List;

@FMMethod("random")
public class RandomGenerator extends FMMethodBase {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.isEmpty()) {
            return RandomUtils.nextInt();
        }
        if (arguments.size() == 1) {
            return RandomUtils.nextInt(0, toInt(arguments.get(0)));
        }
        return RandomUtils.nextInt(toInt(arguments.get(0)), toInt(arguments.get(1)));
    }
}
