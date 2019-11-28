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

@Service("SysCommonHomeListService")
public class SysCommonHomeListService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysCommonHomeListService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";

    @Autowired
    private CacheManager cacheManager;
    
    public ReponseMessage execute(String reqInfo, AppRequestMessage msg, String infoType, String returnStr) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        String versionCode = msg.getVersion();
        if (null != jsonObject.getString(PARAM_VERSIONCODE) && !"".equals(jsonObject.getString(PARAM_VERSIONCODE))) {
            versionCode = jsonObject.getString(PARAM_VERSIONCODE);
        }
        JSONObject retObject = new JSONObject();
        retObject.put(PARAM_VERSIONCODE, versionCode);
        retObject.put(returnStr, cacheManager.getAppHomeInfoContent(msg.getAppType(), versionCode, infoType));
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
