package com.dxt.service;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.dao.CommonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleJobsManager {

    private static final long TEN_MINUTE = 10 * 60 * 1000;
    private static final long ONE_MINUTE = 60 * 1000;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobsManager.class);

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private CommonDao commonDao;

    @Scheduled(fixedDelay = TEN_MINUTE)
    @Async
    public void sessionManageJob() {
        sessionManager.dealSessionExpired();
    }

    @Scheduled(fixedDelay = ONE_MINUTE)
    @Async
    public void checkCacheJob() {
        if (AppConstant.SYS_CONSTANT.CONSTANT_TURE.equals(cacheManager.getSysConfigByCode(
                AppConstant.SYS_CONFIG_KEY.KEY_SYS_DISTRIBUTED_DEPLOYMENT))) {
            try {
                String cacheVerificationFromDB = commonDao.getSysConfigByKey(
                        AppConstant.SYS_CONFIG_KEY.KEY_REFRESH_CACHE_VERIFICATION_CODE);
                if (null == cacheVerificationFromDB || "".equals(cacheVerificationFromDB)) {
                    return;
                }
                if (!cacheVerificationFromDB.equals(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_REFRESH_CACHE_VERIFICATION_CODE))) {
                    cacheManager.initCache();
                }
            } catch (Exception e) {
                logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            }
        }
    }

}
