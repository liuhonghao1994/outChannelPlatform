package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.*;
import com.dxt.dao.VersionBeanDao;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.AppVersionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysVersionService")
public class SysVersionService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysVersionService.class);

    private static final String PARAM_TYPE = "type";
    private static final String PARAM_VERSIONCODE = "versionCode";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private VersionBeanDao versionBeanDao;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        // 业务参数校验
        if (!checkParam(jsonObject)) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        AppRequestMessage msg = getMsg();
        // 获取最新版本信息
        // 需要考虑版本差异大的情况，中间版本有强制升级，不论最新版本是否要求强制升级，都必须强制升级到最新版本
        if (AppConstant.APP_CONSTANT.VERSION_REQUEST_TYPE_LAST.equals(jsonObject.getString(PARAM_TYPE))) {
            AppVersionBean lastVersion = cacheManager.getLastVersionBean(msg.getAppType());
            // 强制升级标识
            boolean forceUpdateFlag = false;
            if (AppConstant.APP_CONSTANT.VERSION_FORCE_UPDATE.equals(lastVersion.getUpdateType())) {
                if (null == msg.getVersion() ||
                        Integer.parseInt(lastVersion.getVersionCode()) > Integer.parseInt(msg.getVersion())) {
                    forceUpdateFlag = true;
                }
            }
            if (!forceUpdateFlag) {
                AppVersionBean forceVersion = cacheManager.getForceVersionBean(msg.getAppType());
                if (null == msg.getVersion() ||
                        Integer.parseInt(forceVersion.getVersionCode()) > Integer.parseInt(msg.getVersion())) {
                    forceUpdateFlag = true;
                }
            }
            JSONObject retObject = new JSONObject();
            retObject.put("versionCode", lastVersion.getVersionCode());
            retObject.put("versionName", lastVersion.getVersionName());
            if (forceUpdateFlag) {
                retObject.put("updateType", AppConstant.APP_CONSTANT.VERSION_FORCE_UPDATE);
            } else {
                retObject.put("updateType", lastVersion.getUpdateType());
            }
            retObject.put("url", lastVersion.getUrl());
            retObject.put("versionDesc", lastVersion.getVersionDesc());
            retObject.put("updateSummary", lastVersion.getUpdateSummary());
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
            return message;
        } else {
            // 获取指定版本信息
            AppVersionBean designatedVersion = versionBeanDao.getDesignatedVersionBeanByType(msg.getAppType(),
                    jsonObject.getString(PARAM_VERSIONCODE));
            if (null == designatedVersion) {
                message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
                return message;
            } else {
                JSONObject retObject = new JSONObject();
                retObject.put("versionCode", designatedVersion.getVersionCode());
                retObject.put("versionName", designatedVersion.getVersionName());
                retObject.put("updateType", designatedVersion.getUpdateType());
                retObject.put("url", designatedVersion.getUrl());
                retObject.put("versionDesc", designatedVersion.getVersionDesc());
                retObject.put("updateSummary", designatedVersion.getUpdateSummary());
                message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
                return message;
            }
        }
    }

    /**
     * 检查业务参数合法性
     * type versionCode
     * @param jsonObject
     * @return
     */
    private boolean checkParam (JSONObject jsonObject) {
        if (null == jsonObject || null == jsonObject.getString(PARAM_TYPE)) {
            return false;
        }
        if (!AppConstant.APP_CONSTANT.VERSION_REQUEST_TYPE_LAST.equals(jsonObject.getString(PARAM_TYPE)) &&
                !AppConstant.APP_CONSTANT.VERSION_REQUEST_TYPE_DESIGNATED.equals(jsonObject.getString(PARAM_TYPE))) {
            return false;
        }
        if (AppConstant.APP_CONSTANT.VERSION_REQUEST_TYPE_DESIGNATED.equals(jsonObject.getString(PARAM_TYPE)) &&
            (null == jsonObject.getString(PARAM_VERSIONCODE) || "".equals(jsonObject.getString(PARAM_VERSIONCODE)))) {
            return false;
        }
        return true;
    }

}
