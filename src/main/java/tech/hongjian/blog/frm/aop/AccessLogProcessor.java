package tech.hongjian.blog.frm.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.frm.annotation.AccessLog;
import tech.hongjian.blog.service.LogService;
import tech.hongjian.blog.utils.JSONUtil;
import tech.hongjian.blog.utils.WebUtil;

/**
 * @author xiahongjian
 * @time 2019-05-27 14:54:05
 *
 */
@Aspect
@Component
public class AccessLogProcessor {
	@Autowired
	private LogService logService;

	@Before("@annotation(accessLog)")
	public void logAccess(JoinPoint joinPoint, AccessLog accessLog) {
		String ip = WebUtil.getRealIp();
		HttpServletRequest request = WebUtil.getRequest();
		String url = request.getRequestURI().toString();
		Integer author = WebUtil.getUid();
		Object[] args = joinPoint.getArgs();

		String[] parameters = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

		Map<String, Object> params = new HashMap<>(parameters.length);
		for (int i = 0; i < parameters.length; i++) {
			if (args[i] instanceof Model || args[i] instanceof HttpServletRequest
					|| args[i] instanceof HttpServletResponse || args[i] instanceof HttpSession) {
				continue;
			}
			params.put(parameters[i], args[i]);
		}
		logService.save(new Log(LogActions.VISIT, JSONUtil.toJson(new RequestLog(url, params, request.getMethod())),
				author, ip));
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RequestLog {
		private String url;
		private Map<String, Object> params;
		private String method;
	}
}
