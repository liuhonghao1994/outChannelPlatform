package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.AppRequestMessage;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AppMineMenu;
import com.dxt.model.AppServerMenu;
import com.dxt.model.AppSessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysMineMenuService")
public class SysMineMenuService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysMineMenuService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";
    private static final String PARAM_MINE = "mine";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private UserInfoService userInfoService;

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

        AppMineMenu appMineMenu = cacheManager.getAppMineMenu(msg.getAppType(),
                PARAM_MINE, versionCode);

        if (null == appMineMenu || 0 == appMineMenu.getFirstLevelList().size()) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        } else {
            JSONObject retObject = new JSONObject();
            retObject.put(PARAM_VERSIONCODE, versionCode);
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_MINEMENU, appMineMenu);
            String userAvatarUrl = "";
            //确定来源，并查看是否已登录。
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                AppSessionInfo appSessionInfo = null;
                try {
                    appSessionInfo = sessionManager.getSessionInfoFromHttpServletRequest(getRequest());
                    if (null != appSessionInfo) {
                        String phone = appSessionInfo.getPhone();
                        userAvatarUrl = userInfoService.getUserAvatarUrl(phone);
                    }
                    if (null == userAvatarUrl) {
                        userAvatarUrl = "";
                    }
                } catch (MyBusiException e) {
                    logger.error(LogHelper._FUNC_EXCEPTION_(), e);
                }
            }
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_USERAVATARURL, userAvatarUrl);
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
            return message;
        }
    }


}
