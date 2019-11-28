package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.BalanceInfo;
import com.dxt.boss.model.RealTimeBillInfo;
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

@Service("GetRealTimeBillInfoService")
public class GetRealTimeBillInfoService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetRealTimeBillInfoService.class);

    private static final String PARAM_TYPE = "type";

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
        RealTimeBillInfo realTimeBillInfo = phoneInfoService.getUserRealTimeBill(map);
        if (null == realTimeBillInfo) {
            logger.error(LogHelper._FUNC_() + "realTimeBillInfo is null!");
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_REALTIMEBILLINFO, realTimeBillInfo);
        // 如果不需要余额信息，就直接返回
        if (null == jsonObject.getString(PARAM_TYPE) || !"1".equals(jsonObject.getString(PARAM_TYPE))) {
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
            return message;
        }
        BalanceInfo balanceInfo = phoneInfoService.getAccountBalance(map);
        if (null == balanceInfo) {
            logger.error(LogHelper._FUNC_() + "balanceInfo is null!");
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_BALANCEINFO, new BalanceInfo());
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        // 返回值
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_BALANCEINFO, balanceInfo);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
