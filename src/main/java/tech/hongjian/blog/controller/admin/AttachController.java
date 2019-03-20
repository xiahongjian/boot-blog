package tech.hongjian.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tech.hongjian.blog.consts.BlogConsts;
import tech.hongjian.blog.consts.LogActions;
import tech.hongjian.blog.consts.Types;
import tech.hongjian.blog.controller.BaseController;
import tech.hongjian.blog.db.entity.Attach;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.service.AttachService;
import tech.hongjian.blog.service.LogService;
import tech.hongjian.blog.service.OptionService;
import tech.hongjian.blog.service.SiteService;
import tech.hongjian.blog.utils.BlogUtils;
import tech.hongjian.blog.utils.RestResponse;
import tech.hongjian.blog.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("admin/attach")
public class AttachController extends BaseController {
    @Autowired
    private AttachService attachService;

    @Autowired
    private OptionService optionService;

    @Autowired
    private LogService logService;

    @Autowired
    private SiteService siteService;

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int page,
                        @RequestParam(name = "limit", defaultValue = "15") int limit) {

        PageInfo<Attach> attaches = attachService.select(page, limit);
        model.addAttribute("attaches", attaches);
        model.addAttribute(Types.ATTACH_URL,
                optionService.getOptionValue(Types.ATTACH_URL));
        model.addAttribute("max_file_size", BlogConsts.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }


    @ResponseBody
    @PostMapping("/upload")
    public RestResponse upload(HttpServletRequest request) {

        Map<String, MultipartFile> files = ((MultipartHttpServletRequest) request).getFileMap();
        if (files == null || files.isEmpty()) {
            return RestResponse.fail("缺少参数，请重试。");
        }
        List<Attach> errorAttaches = new ArrayList<>();
        List<Attach> attaches = new ArrayList<>();
        Date now = new Date();
        files.forEach((k, f) -> {
            if (f.isEmpty()) {
                return;
            }
            String name = f.getOriginalFilename();
            long size = f.getSize() / 1024;
            if (size > BlogConsts.MAX_FILE_SIZE) {
                Attach attach = new Attach();
                attach.setFname(name);
                errorAttaches.add(attach);
            } else {
                String fkey = BlogUtils.getFileKey(name);
                String ftype = f.getContentType().contains("image") ? Types.IMAGE :
                        Types.FILE;
                String filePath = BlogUtils.getFilePath(fkey);

                try {
                    Files.write(Paths.get(filePath), f.getBytes());
                } catch (IOException e) {
                    log.error("Failed to write the upload file to the disk.", e);
                }
                Attach attach = new Attach();
                attach.setFname(name);
                attach.setFkey(fkey);
                attach.setFtype(ftype);
                attach.setAuthorId(getUid());
                attach.setCreated(now);
                attaches.add(attach);
            }
        });
        if (!errorAttaches.isEmpty()) {
            return RestResponse.builder().success(false).payload(errorAttaches).build();
        }
        attachService.save(attaches);
        siteService.clearStatistics();
        return RestResponse.ok(attaches);
    }

    @ResponseBody
    @PostMapping("delete")
    public RestResponse delete(Integer id) {
        Attach attach = attachService.selectById(id);
        if (null == attach) {
            return RestResponse.fail("该附件不存在。");
        }
        String fkey = attach.getFkey();
        Path path = Paths.get(BlogUtils.getFilePath(fkey));
        if (!Files.exists(path)) {
            return RestResponse.fail("该附件不存在。");
        }
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", path.toString(), e);
            return RestResponse.fail("删除附件失败。");
        }
        attachService.delete(id);
        logService.save(new Log(LogActions.DEL_ARTICLE, fkey, getUid(),
                WebUtil.getRealIp()
        ));
        return RestResponse.ok();
    }
}
