package tech.hongjian.blog.frm.freemarker;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;

public abstract class FMMethodBase implements TemplateMethodModelEx {
    protected int toInt(Object n) {
        return ((SimpleNumber) n).getAsNumber().intValue();
    }

    protected String toStr(Object s) {
        if (s instanceof SimpleScalar) {
            return ((SimpleScalar) s).getAsString();
        }

        if (s instanceof SimpleNumber) {
            return ((SimpleNumber) s).getAsNumber().toString();
        }

        return null;
    }
}
