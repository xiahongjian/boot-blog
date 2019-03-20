package tech.hongjian.blog.frm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.hongjian.blog.frm.exception.ServiceException;
import tech.hongjian.blog.utils.JSONUtil;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(ServiceException.class)
    public String serviceExceptionHandler(RedirectAttributes attrs, HttpServletRequest request, ServiceException e) {
        log.warn("Some errors have been occurred, message: {}", e.getMessage(), e);
        if (WebUtil.isAjaxRequest(request)) {
            return JSONUtil.toJson(RestResponse.fail(e.getMessage()));
        }
        attrs.addFlashAttribute("errMsg", e.getMessage());
        return "redirect:/error/500";
    }

}
