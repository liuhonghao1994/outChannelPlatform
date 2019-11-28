package com.dxt.service;

import javax.servlet.http.HttpSession;

import com.dxt.boss.model.BalanceInfo;
import com.dxt.boss.model.RealTimeBillInfo;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.MyBusiException;
import com.dxt.gexin.common.GexinConstant;
import com.dxt.gexin.service.PushToSingleService;
import com.dxt.model.AppUserBasicInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AppSessionInfo;

import java.util.HashMap;
import java.util.Map;

@Service("AppUserLoginService")
public class AppUserLoginService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppUserLoginService.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PhoneInfoService phoneInfoService;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private PushToSingleService pushToSingleService;


    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            //校验输入参数
            checkParams(jsonObject, AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE,
                    AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PASSWORD,
                    AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_IMEI,
                    AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PUSHID);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE,
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PASSWORD,
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PASSWORD));
        //是否需要保持登陆
        String effective = jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_KEEPLOGIN);
        // session缺省为不保持登录
        if (null == effective || "".equals(effective)) {
            effective = AppConstant.SYS_CONSTANT.APPUSER_SESSION_NO_EFFECTIVE;
        }

        // 验证用户密码
        if (!userInfoService.verifyUserPassword(map)) {
            logger.info(LogHelper._FUNC_() + "密码校验未通过！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_LOGIN_PASS_ERROR_MSG);
            return message;
        }
        // 获取用户信息
        AppUserBasicInfoBean appUserBasicInfoBean = userInfoService.getUserBasicInfo(map);
        // 获取号码网络信息
        String network = phoneInfoService.getCarrierOperator(map);
        appUserBasicInfoBean.setNetwork(network);
        // 余额信息
        BalanceInfo balanceInfo = phoneInfoService.getAccountBalance(map);
        // 实时话费
        RealTimeBillInfo realTimeBillInfo = phoneInfoService.getUserRealTimeBill(map);
        // 使用量 确定暂时登录时不展示

        // 返回值
        JSONObject retObject = new JSONObject();
        // 通过phone查找是否有生效session，如果有且设备不同，则进行失效
        AppSessionInfo appSessionInfo = sessionManager.getSessionByPhone(
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        if (null == appSessionInfo) {
            // 创建session
            String sessionId = createSession(appUserBasicInfoBean,
                    jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_IMEI),
                    jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PUSHID),
                    effective);
            retObject.put(cacheManager.getSysConfigByCode(
                    AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_ID_STR), sessionId);
        } else {
            // 如果设备号相同，认定同一设备，则使用原sessionid
            if (jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_IMEI).equals(appSessionInfo.getImei())) {
                sessionManager.updateSessionInfo(appSessionInfo);
                retObject.put(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_ID_STR), appSessionInfo.getSessionId());
            } else {
                String oldPushId = appSessionInfo.getPushId();
                String oldAppType = appSessionInfo.getOsType();
                // 进行推送，在原设备提示异地登录
                sendPushMsgToOldDevice(oldPushId, appSessionInfo.getPhone(), oldAppType);
                // 将原session失效
                sessionManager.removeSession(appSessionInfo.getSessionId());
                // 创建新session
                String sessionId = createSession(appUserBasicInfoBean,
                        jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_IMEI),
                        jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PUSHID),
                        effective);
                retObject.put(cacheManager.getSysConfigByCode(
                        AppConstant.SYS_CONFIG_KEY.KEY_APP_SESSION_ID_STR), sessionId);
            }
        }
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_USERINFO, appUserBasicInfoBean);
        if (null != balanceInfo) {
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_BALANCEINFO, balanceInfo);
        } else {
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_BALANCEINFO, new BalanceInfo());
        }
        if (null != realTimeBillInfo) {
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_REALTIMEBILLINFO, realTimeBillInfo);
        } else {
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_REALTIMEBILLINFO, new RealTimeBillInfo());
        }
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_LOGIN_OK_MSG, retObject);
        
        return message;
    }


    private String createSession(AppUserBasicInfoBean appUserBasicInfoBean, String imei,
                                 String pushId, String effective) {
        logger.debug(LogHelper._FUNC_START_());
        HttpSession session = getRequest().getSession();
        String sessionId = "";
        if (null != session) {
            sessionId = session.getId();
            AppSessionInfo appSessionInfo = new AppSessionInfo();
            appSessionInfo.setPhone(appUserBasicInfoBean.getPhone());
            appSessionInfo.setImei(imei);
            appSessionInfo.setPushId(pushId);
            appSessionInfo.setSessionId(sessionId);
            appSessionInfo.setEffective(effective);
            appSessionInfo.setUserId(appUserBasicInfoBean.getUserId());
            appSessionInfo.setAcctId(appUserBasicInfoBean.getAcctId());
            appSessionInfo.setNetwork(appUserBasicInfoBean.getNetwork());
            appSessionInfo.setOsType(getAppRequestOsType());
            sessionManager.saveSessionInfo(appSessionInfo);
        } else {
            logger.error(LogHelper._FUNC_() + "未获取session！");
        }
        return sessionId;
    }


    private void sendPushMsgToOldDevice(String oldPushId, String phone, String oldAppType) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(GexinConstant.REQUEST_PARAM.PARAM_TYPE,
                AppConstant.REQUEST_SOURCE.APP.getValue() + "_" + oldAppType + "_");
        map.put(GexinConstant.REQUEST_PARAM.CLIENT_ID, oldPushId);
        map.put(GexinConstant.REQUEST_PARAM.ALIAS, phone);
        map.put(GexinConstant.REQUEST_PARAM.TITLE, "您的账号在其他设备登录！");
        map.put(GexinConstant.REQUEST_PARAM.TEXT, "如果不是您本人操作，请尽快修改密码！您也可以重新登录！");
        map.put(GexinConstant.REQUEST_PARAM.LOGO, "");
        map.put(GexinConstant.REQUEST_PARAM.LOGO_URL, "");
        map.put(GexinConstant.REQUEST_PARAM.LINK_URL, "");
        try {
            pushToSingleService.dealPush(map);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_(), e);
        }
    }

}
