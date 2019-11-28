package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysHomeAllInfoListService")
public class SysHomeAllInfoListService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysHomeAllInfoListService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";

    @Autowired
    private CacheManager cacheManager;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        AppRequestMessage msg = getMsg();
        String versionCode = msg.getVersion();
        if (null != jsonObject.getString(PARAM_VERSIONCODE) && !"".equals(jsonObject.getString(PARAM_VERSIONCODE))) {
            versionCode = jsonObject.getString(PARAM_VERSIONCODE);
        }
        JSONObject retObject = new JSONObject();
        retObject.put(PARAM_VERSIONCODE, versionCode);
        retObject.put(AppConstant.APP_CONSTANT.HOME_STR_LIST_FUCTION, cacheManager.getAppHomeInfoContent(
                msg.getAppType(), versionCode, AppConstant.APP_CONSTANT.HOME_TYPE_FUNCTION));
        retObject.put(AppConstant.APP_CONSTANT.HOME_STR_LIST_NEWS, cacheManager.getAppHomeInfoContent(
                msg.getAppType(), versionCode, AppConstant.APP_CONSTANT.HOME_TYPE_NEWS));
        retObject.put(AppConstant.APP_CONSTANT.HOME_STR_LIST_AD, cacheManager.getAppHomeInfoContent(
                msg.getAppType(), versionCode, AppConstant.APP_CONSTANT.HOME_TYPE_AD));
        retObject.put(AppConstant.APP_CONSTANT.HOME_STR_LIST_PROMOTION, cacheManager.getAppHomeInfoContent(
                msg.getAppType(), versionCode, AppConstant.APP_CONSTANT.HOME_TYPE_PROMOTION));

        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }




}
