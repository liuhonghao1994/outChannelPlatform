package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.BalanceInfo;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AppSessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("GetPhoneBalanceInfoService")
public class GetPhoneBalanceInfoService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetPhoneBalanceInfoService.class);

    @Autowired
    private PhoneInfoService phoneInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        String phone;
        try {
            phone = getPhoneFromSessionOrParam(jsonObject, source);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_LOGIN_FIRST_MSG);
            return message;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
        BalanceInfo balanceInfo = phoneInfoService.getAccountBalance(map);
        if (null == balanceInfo) {
            logger.error(LogHelper._FUNC_() + "");
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_BALANCEINFO,
                balanceInfo);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);

        return message;
    }



}
