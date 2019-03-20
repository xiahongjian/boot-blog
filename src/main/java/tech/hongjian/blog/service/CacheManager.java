package tech.hongjian.blog.service;

import org.springframework.stereotype.Service;
import tech.hongjian.blog.utils.MapCache;

@Service
public class CacheManager extends MapCache {

    public CacheManager() {
        single();
    }
}
