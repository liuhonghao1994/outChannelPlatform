package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.service.BusiProcessService;
import com.dxt.common.*;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("BusiChangeUserMainOfferService")
public class BusiChangeUserMainOfferService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(BusiChangeUserMainOfferService.class);

    @Autowired
    private BusiProcessService busiProcessService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_OFFERID);
            } else {
                checkParams(jsonObject, AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_OFFERID,
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
        String inOfferId = jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_OFFERID);

        Map<String, Object> map = new HashMap<>(2);
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_OFFERID,
                String.valueOf(Long.parseLong(inOfferId) - AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE));
        HashMap<String, Object> retMap = busiProcessService.updateUserMainOffer(map);
        if (null == retMap) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        // 返回值
        if (AppConstant.REPONSE_CODE.OK.equals(retMap.get(AppConstant.REPONSE_CODE.CODE).toString())) {
            message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            return message;
        } else {
            message.setMsg(retMap.get(AppConstant.REPONSE_CODE.CODE).toString(),
                    retMap.get(AppConstant.REPONSE_MSG.MSG).toString());
            return message;
        }
    }

}
