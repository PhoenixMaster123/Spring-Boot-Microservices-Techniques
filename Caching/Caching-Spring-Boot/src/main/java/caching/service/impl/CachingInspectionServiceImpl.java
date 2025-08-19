package caching.service.impl;

import caching.service.CachingInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CachingInspectionServiceImpl implements CachingInspectionService {

    private final CacheManager cacheManager;

    @Autowired
    public CachingInspectionServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void printCacheInfo(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);

        if(cache != null) {
            System.out.println("Cache Contents:");
            System.out.println(Objects.requireNonNull(cache.getNativeCache()));
        } else {
            System.out.println("No such cache: " + cacheName);
        }
    }
}
