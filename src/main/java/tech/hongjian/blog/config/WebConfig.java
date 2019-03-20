package tech.hongjian.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.hongjian.blog.frm.annotation.Interceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private ApplicationContext appCtx;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<InterceptorDefinition> interceptors = new ArrayList<>();

        // 获取所有包含@Iterceptor注解的bean，并将其注册
        appCtx.getBeansWithAnnotation(Interceptor.class).forEach((key, value) -> {
            if (!(value instanceof HandlerInterceptor))
                return;
            Interceptor annotation = value.getClass().getAnnotation(Interceptor.class);
            interceptors.add(new InterceptorDefinition(annotation.path(),
                    annotation.order(), (HandlerInterceptor) value));
        });
        // 根据order排序后再注册
        Collections.sort(interceptors, Comparator.comparingInt(o -> o.order));
        interceptors.forEach(definition -> {
            registry.addInterceptor(definition.interceptor).addPathPatterns(definition.path);
            log.info("Add interceptor {}, path: {]", definition.interceptor.getClass().getName(), StringUtils.join(definition.path));
        });
    }

    private static class InterceptorDefinition {
        int order;
        String[] path;
        HandlerInterceptor interceptor;

        public InterceptorDefinition(String[] path, int order,
                                     HandlerInterceptor interceptor) {
            this.order = order;
            this.path = path;
            this.interceptor = interceptor;
        }
    }
}
