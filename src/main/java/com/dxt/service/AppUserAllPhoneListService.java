package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.BaseCustInfo;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AppUserAllPhoneListService")
public class AppUserAllPhoneListService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(AppUserAllPhoneListService.class);

    private static final String PARAM_LAST4CODE = "last4Code";
    private static final String PARAM_OUT_CERTCARDCODE = "CertCardCode";

    @Autowired
    private UserInfoService userInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, PARAM_LAST4CODE);
            } else {
                checkParams(jsonObject, PARAM_LAST4CODE,
                        AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
            }
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }
        String phone;
        try {
            phone = getPhoneFromSessionOrParam(jsonObject, source);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_LOGIN_FIRST_MSG);
            return message;
        }

        Map<String, Object> map = new HashMap<>();
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
        BaseCustInfo baseCustInfo = userInfoService.getBaseCustInfo(map);
        if (null == baseCustInfo) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        String certCode = baseCustInfo.getCustCertCode().substring(0, 14) + jsonObject.getString(PARAM_LAST4CODE);
        logger.debug(LogHelper._FUNC_() + "certCode:" + certCode);
        List<String> list = userInfoService.getCustomerPhoneNumber(certCode);
        if (null == list || 0 == list.size()) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        // 轮询每个号码的余额 TODO

        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
        return message;
    }

}
