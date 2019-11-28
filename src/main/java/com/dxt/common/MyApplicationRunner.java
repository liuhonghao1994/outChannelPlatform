package com.dxt.common;

import com.dxt.service.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        cacheManager.initCache();
    }
}
