package tech.hongjian.blog.frm;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tech.hongjian.blog.consts.BlogConsts;

@Component
public class ErrorPageConfig implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, BlogConsts.ERROR_URI + "/404");
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, BlogConsts.ERROR_URI + "/500");
        registry.addErrorPages(error404, error500);
    }
}
