package tech.hongjian.blog.db.entity.biz;

import lombok.Data;
import lombok.NoArgsConstructor;
import tech.hongjian.blog.db.entity.Log;

/**
 * @author xiahongjian
 * @time 2019-07-08 18:35
 */
@Data
@NoArgsConstructor
public class BizLog extends Log {

    public BizLog(Log log) {
        this.setAction(log.getAction());
        this.setAuthorId(log.getAuthorId());
        this.setCreated(log.getCreated());
        this.setData(log.getData());
        this.setId(log.getId());
        this.setIp(log.getIp());
    }
    private int cityId;
    private String region;
}
