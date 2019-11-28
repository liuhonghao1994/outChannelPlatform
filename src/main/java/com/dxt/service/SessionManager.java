package com.dxt.service;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.dao.AppSessionInfoDao;
import com.dxt.model.AppSessionInfo;
import com.dxt.model.BusiCodeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service("SessionManager")
public class SessionManager {
    
    private static ConcurrentHashMap<String, AppSessionInfo> sessionMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(SessionManager.class);

    @Autowired
    private AppSessionInfoDao appSessionInfoDao;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 通过cookie得到sessionid
     * @param request
     * @return
     */
    public String getSessionIdFromHttpServletRequest(HttpServletRequest request) {
        logger.debug(LogHelper._FUNC_START_());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_ID_STR))) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * session创建后，进行保存
     * @param session
     */
    public void saveSessionInfo(AppSessionInfo session) {
        logger.debug(LogHelper._FUNC_START_());
        try {
            sessionMap.put(session.getSessionId(), session);
            appSessionInfoDao.insertAppSessionInfo(session);
        } catch (Exception e) {
            logger.error("saveSessionInfo-Exception:", e);
        }
    }

    /**
     * 更新session信息，主要是更新最后登录时间
     * @param session
     * @return
     */
    public boolean updateSessionInfo(AppSessionInfo session) {
        logger.debug(LogHelper._FUNC_START_());
        try {
            // 如果是长期有效的，则不进行修改；否则，修改最后登录时间
            // 此处只修改物理库，不修改map，目的是分布式部署，数据统一问题
            if (AppConstant.SYS_CONSTANT.APPUSER_SESSION_NO_EFFECTIVE.equals(session.getEffective())) {
                logger.debug(LogHelper._FUNC_() + "update db!");
                int updateNum = appSessionInfoDao.updateAppSessionInfoLastLoginTime(session);
                if (0 == updateNum) {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return false;
        }
        return true;
    }

    /**
     * 通过sessionId获取有效的AppSessionInfo
     * @param sessionId
     * @return
     */
    private AppSessionInfo getSessionInfoBySessionId(String sessionId) {
        logger.debug(LogHelper._FUNC_START_());
        AppSessionInfo appSessionInfo = sessionMap.get(sessionId);
        // 如果map里没有获取到，从数据库读取，然后保存到map
        if (null == appSessionInfo) {
            logger.debug(LogHelper._FUNC_() + "map is null! now get from db!");
            appSessionInfo = getEffectiveAppSessionInfoBySessionIdFromDB(sessionId);
            return appSessionInfo;
        } else {
            // 从map里获取的session，可能存在map过期而实际db未过期的情况，此时需要从数据库重新读取
            if (!isValidSession(appSessionInfo)) {
                logger.debug(LogHelper._FUNC_() + "map is outtime! now get from db!");
                appSessionInfo = getEffectiveAppSessionInfoBySessionIdFromDB(sessionId);
            }
            return appSessionInfo;
        }
    }

    /**
     * 通过号码获取session，这种情况只能从数据库获取
     * @param phone
     * @return
     */
    public AppSessionInfo getSessionByPhone(String phone) {
        logger.debug(LogHelper._FUNC_START_());
        // 从数据库获取
        AppSessionInfo appSessionInfo = appSessionInfoDao.getEffectiveAppSessionInfoByPhone(phone,
                Long.parseLong(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_EFFECTIVE_TIME)));
        if (null == appSessionInfo) {
            return null;
        }
        // 更新map
        setSessionCache(appSessionInfo);
        return appSessionInfo;
    }

    /**
     * 对session进行移除
     * @param sessionId
     */
    public void removeSession(String sessionId) {
        logger.debug(LogHelper._FUNC_START_());
        if (sessionMap.containsKey(sessionId)) {
            sessionMap.remove(sessionId);
        }
        appSessionInfoDao.deleteAppSessionInfo(sessionId);
    }

    /**
     * 只添加缓存里的session信息
     * @param session
     */
    private void setSessionCache(AppSessionInfo session) {
        logger.debug(LogHelper._FUNC_START_());
        try {
            logger.debug(LogHelper._FUNC_() + "update map!");
            sessionMap.put(session.getSessionId(), session);
        } catch (Exception e) {
            logger.error("saveSessionInfo-Exception:", e);
        }
    }

    /**
     * 从数据库获取session，并且put到map里
     * @param sessionId
     * @return
     */
    private AppSessionInfo getEffectiveAppSessionInfoBySessionIdFromDB(String sessionId) {
        AppSessionInfo appSessionInfo = appSessionInfoDao.getEffectiveAppSessionInfoBySessionId(sessionId,
                Long.parseLong(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_EFFECTIVE_TIME)));
        if (null == appSessionInfo) {
            return null;
        } else {
            setSessionCache(appSessionInfo);
            return appSessionInfo;
        }
    }

    /**
     * 判断某session是否是有效session
     * @param appSessionInfo
     * @return
     */
    private boolean isValidSession(AppSessionInfo appSessionInfo) {
        if (null == appSessionInfo) {
            return false;
        }
        if (!AppConstant.SYS_CONSTANT.APPUSER_SESSION_NO_EFFECTIVE.equals(appSessionInfo.getEffective())) {
            return true;
        }
        if (appSessionInfo.getLastLoginTime().getTime() +
                (Long.parseLong(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_EFFECTIVE_TIME)) * 1000) <
                System.currentTimeMillis()) {
            return false;
        }
        return true;
    }



    /**
     * @desc 校验APP用户权限
     * @param request
     * @param codeBean
     * @return
     */
    public boolean checkAppUserAuthorization(HttpServletRequest request, BusiCodeBean codeBean) throws MyBusiException {
        logger.debug(LogHelper._FUNC_START_());
        // 需要登录且校验权限
        if (AppConstant.SYS_CONSTANT.REQUIRE_LOGIN.equals(codeBean.getRequireLogin())) {
            logger.info(codeBean.getBeanName() + " require login");
            String sessionId = getSessionIdFromHttpServletRequest(request);
            if (sessionId == null || "".equals(sessionId)) {
                logger.info("session不存在，请重新登录！");
                throw new MyBusiException("session不存在，请重新登录！");
            }
            AppSessionInfo appSessionInfo = getSessionInfoBySessionId(sessionId);
            if (appSessionInfo == null) {
                logger.info("session不匹配或已过期，请重新登录！");
                throw new MyBusiException("session不匹配或已过期，请重新登录！");
            }
            // 如果更新失败，则要求重新登录
            if (!updateSessionInfo(appSessionInfo)) {
                logger.info("session已过期，请重新登录！");
                throw new MyBusiException("session已过期，请重新登录！");
            }
            return true;
        } else {
            // 不需要登录
            return true;
        }
    }

    public AppSessionInfo getSessionInfoFromHttpServletRequest(HttpServletRequest request) throws MyBusiException {
        logger.debug(LogHelper._FUNC_START_());
        String sessionId = getSessionIdFromHttpServletRequest(request);
        if (null == sessionId || "".equals(sessionId)) {
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_LOGIN_FIRST_MSG);
        }
        AppSessionInfo appSessionInfo = getSessionInfoBySessionId(sessionId);
        if (null == appSessionInfo) {
            throw new MyBusiException(AppConstant.REPONSE_MSG.SYS_LOGIN_FIRST_MSG);
        } else {
            return appSessionInfo;
        }
    }

    public void dealSessionExpired() {
        logger.info("sessionMap.size():" + sessionMap.size());
        Iterator<Map.Entry<String, AppSessionInfo>> iterator = sessionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, AppSessionInfo> entry = iterator.next();
            AppSessionInfo appSessionInfo = entry.getValue();
            // 如果已过期，则进行移除，释放map空间
            // 这里不区分是否长期有效，只要超时就进行移除。对于长期有效的，下次请求会再次从数据库加载
            if (appSessionInfo.getLastLoginTime().getTime() +
                    (Long.parseLong(cacheManager.getSysConfigByCode(
                            AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_EFFECTIVE_TIME)) * 1000) <
                    System.currentTimeMillis()) {
                logger.debug(appSessionInfo.getSessionId() + "will be removed!");
                iterator.remove();
            }
        }
        // 进行数据库失效session删除
        appSessionInfoDao.deleteAppSessionInfoBatch(Long.parseLong(cacheManager.getSysConfigByCode(
                AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_EFFECTIVE_TIME)));
    }

    /**
     * 清除session缓存
     * @return
     */
    public boolean clearSessionMap() {
        if (null == sessionMap) {
            sessionMap = new ConcurrentHashMap<>();
            return true;
        }
        sessionMap.clear();
        return true;
    }

}
