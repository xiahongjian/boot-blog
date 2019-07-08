package tech.hongjian.blog.db.entity.biz.converter;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.util.ResourceUtils;
import tech.hongjian.blog.db.entity.Log;
import tech.hongjian.blog.db.entity.biz.BizLog;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author xiahongjian
 * @time 2019-07-08 18:41
 */
@Slf4j
public class Log2BizLogConverter implements Converter<Log, BizLog> {
    public DbSearcher dbSearcher;

    public Log2BizLogConverter() {
        try {
            DbConfig config = new DbConfig();
            String path = ResourceUtils.getFile("classpath:ip/ip2region.db").getPath();
            dbSearcher = new DbSearcher(config, path);
        } catch (DbMakerConfigException e) {
            log.error("Failed to create DbConfig class instance, {}", e.getMessage(), e);
        } catch (FileNotFoundException e) {
            log.error("Failed to find ip db file, {}.", e.getMessage(), e);
        }
    }

    @Override
    public BizLog convert(Log o) {
        BizLog bizLog = new BizLog(o);
        try {
            DataBlock dataBlock = dbSearcher.btreeSearch(o.getIp());
            bizLog.setCityId(dataBlock.getCityId());
            bizLog.setRegion(dataBlock2str(dataBlock));
        } catch (IOException e) {
            log.warn("Search ip location error, {}.", e.getMessage(), e);
        }
        return bizLog;
    }

    public void release() {
        try {
            dbSearcher.close();
        } catch (IOException e) {
            log.warn("Failed to close the DbSearch instance, {}.", e.getMessage(), e);
        }
    }

    private String dataBlock2str(DataBlock dataBlock) {
        String[] items = dataBlock.getRegion().split("\\|");
        String s = "";
        for (int i = 0; i < 4; i++) {
            if (!"0".equals(items[i])) {
                s += items[i];
            }
        }
        if (!"0".equals(items[4])) {
            s += "(" + items[4] + ")";
        }
        return s;
    }
}
