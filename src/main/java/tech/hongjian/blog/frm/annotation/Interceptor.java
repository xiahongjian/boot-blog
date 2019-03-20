package tech.hongjian.blog.frm.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Interceptor {
    /**
     * 拦截路径
     */
    String[] path();

    /**
     * 拦截器排列顺（从小大依次注册）
     */
    int order() default 100;
}
