package tech.hongjian.blog.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import tech.hongjian.blog.frm.annotation.FMDirective;
import tech.hongjian.blog.frm.annotation.FMMethod;

import java.util.HashMap;
import java.util.Map;

@Component
public class FreemarkerConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext appCtx;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private FreeMarkerViewResolver freeMarkerViewResolver;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 配置自定义指令
        setCustomDirective();
        // 配置自定方法
        setCustomMethod();
    }

    private void setCustomDirective() {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        Map<String, Object> directiveMap = appCtx.getBeansWithAnnotation(FMDirective.class);
        directiveMap.entrySet().forEach(entry -> {
            Object value = entry.getValue();
            if (!(value instanceof TemplateModel))
                return;
            String name = getDirectiveName(value);
            configuration.setSharedVariable(name, (TemplateModel) value);

        });
    }

    private void setCustomMethod() {
        if (freeMarkerViewResolver == null)
            return;
        Map<String, Object> methodMap = appCtx.getBeansWithAnnotation(FMMethod.class);
        Map<String, TemplateMethodModelEx> map = new HashMap<>(methodMap.size());
        methodMap.entrySet().forEach(entry -> {
            Object value = entry.getValue();
            if (!(value instanceof TemplateMethodModelEx))
                return;
            String name = getMethodName(value);
            map.put(name, (TemplateMethodModelEx) value);
        });
        freeMarkerViewResolver.setAttributesMap(map);
    }

    private String getMethodName(Object bean) {
        FMMethod method = bean.getClass().getAnnotation(FMMethod.class);
        String name = method.value();
        return "".equals(name) ? uncapitalizeFirst(bean.getClass().getSimpleName()) : name;
    }

    private String getDirectiveName(Object bean) {
        FMDirective directive = bean.getClass().getAnnotation(FMDirective.class);
        String name = directive.value();
        return "".equals(name) ? uncapitalizeFirst(bean.getClass().getSimpleName()) : name;
    }

    
    private String uncapitalizeFirst(String str) {
        char first = str.charAt(0);
        if (first >= 'A' && first <= 'Z') {
            char[] chars = str.toCharArray();
            chars[0] += 32;
            return new String(chars);
        }
        return str;
    }
}
