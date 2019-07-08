package tech.hongjian.blog.consts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author xiahongjian
 * @time 2019-07-08 20:09
 */
@Slf4j
public class IPDataConsts {
    public static final byte[] IP_DATA;

    static {
        byte[] data = null;
        try {
            data = Files.readAllBytes(Paths.get(ResourceUtils.getURL(
                        "classpath:ip/ip2region.db").toURI()));
        } catch (IOException e) {
            log.error("Failed to read data from ip2region.db, {}", e.getMessage(), e);
        } catch (URISyntaxException e) {
        }
        IP_DATA = data;
    }
}
