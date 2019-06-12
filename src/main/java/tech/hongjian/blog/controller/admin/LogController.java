package tech.hongjian.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.service.LogService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("admin/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "limit", defaultValue = "15") int limit,
                        String action,
                        String keyword,
                        Integer author,
                        String ip,
                        String from,
                        String to) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null, toDate = null;
        try {
            if (StringUtils.isNotBlank(from))
                fromDate = dateFormat.parse(from);
            if (StringUtils.isNoneBlank(to))
                toDate = dateFormat.parse(to);
        } catch (ParseException e) {
            log.warn("Failed to parse string to date, string: {}, {}", from, to, e);
        }
        PageInfo<Log> logs = logService.selectByParams(action, keyword, author, ip,
                fromDate, toDate, page, limit);
        model.addAttribute("logs", logs);
        setQueryParameters(model, action, keyword, author, from, to);
        return "admin/log_list";
    }

    private void setQueryParameters(Model model, String action, String keyword,
                                    Integer author, String from, String to) {
        model.addAttribute("actionsMap", LogActions.getActionMap());
        if (StringUtils.isNoneBlank(action)) {
            model.addAttribute("action", action);
        }
        if (StringUtils.isNoneBlank(keyword)) {
            model.addAttribute("keyword", keyword);
        }
        if (author != null) {
            model.addAttribute("author", author);
        }
        if (StringUtils.isNoneBlank(from)) {
            model.addAttribute("from", from);
        }
        if (StringUtils.isNoneBlank("to")) {
            model.addAttribute("to", to);
        }
    }
}
