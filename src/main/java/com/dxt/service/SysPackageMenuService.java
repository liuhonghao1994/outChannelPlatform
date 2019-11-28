package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.AppRequestMessage;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AppPackageMenu;
import com.dxt.model.AppSessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SysPackageMenuService")
public class SysPackageMenuService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(SysPackageMenuService.class);

    private static final String PARAM_VERSIONCODE = "versionCode";
    private static final String PARAM_PACKAGE = "package";

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private PhoneInfoService phoneInfoService;

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
        // 判断是否有session，如果有，获取对应网络的流量包，否则获取全部
        AppSessionInfo appSessionInfo = null;
        try {
            appSessionInfo = sessionManager.getSessionInfoFromHttpServletRequest(getRequest());
        } catch (MyBusiException e) {
        }
        String network = null;
        if (null != appSessionInfo) {
            network = appSessionInfo.getNetwork();
        }
        AppPackageMenu appPackageMenu = cacheManager.getAppPackageMenu(msg.getAppType(),
                PARAM_PACKAGE, versionCode, network);
        if (null == appPackageMenu || 0 == appPackageMenu.getFirstLevelList().size()) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        } else {
            JSONObject retObject = new JSONObject();
            retObject.put(PARAM_VERSIONCODE, versionCode);
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_PACKAGEMENU, appPackageMenu);
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
            return message;
        }
    }


}
