package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.AppServerMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysServerMenuService")
public class SysServerMenuService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysServerMenuService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";
    private static final String PARAM_SERVER = "server";

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

        AppServerMenu appServerMenu = cacheManager.getAppServerMenu(msg.getAppType(),
                PARAM_SERVER, versionCode);

        if (null == appServerMenu || 0 == appServerMenu.getFirstLevelList().size()) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        } else {
            JSONObject retObject = new JSONObject();
            retObject.put(PARAM_VERSIONCODE, versionCode);
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_SERVERMENU, appServerMenu);
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
            return message;
        }
    }


}
