package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.UsedResInfo;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyDateUtil;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AppSessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("GetUserUsedResService")
public class GetUserUsedResService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetUserUsedResService.class);

    private static final String PARAM_CURRENTDATE = "currentDate";

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
        UsedResInfo usedResInfo = phoneInfoService.getUserUsedRes(map);
        if (null == usedResInfo) {
            logger.error(LogHelper._FUNC_() + "FreeResourceInfo is null!");
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_USEDRESINFO, usedResInfo);
        retObject.put(PARAM_CURRENTDATE, MyDateUtil.getDateStringFromDate(new Date(),
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern()));
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
