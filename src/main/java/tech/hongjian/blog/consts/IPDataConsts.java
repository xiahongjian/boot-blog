package tech.hongjian.blog.consts;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.io.InputStream;

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
            InputStream in = new DefaultResourceLoader().getResource("classpath:ip/ip2region.db").getInputStream();
            data = IOUtils.toByteArray(in);
        } catch (IOException e) {
            log.error("Failed to read data from ip2region.db, {}", e.getMessage(), e);
        }
        IP_DATA = data;
    }
}
