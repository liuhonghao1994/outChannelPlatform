package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.PayAndWriteoffRec;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.*;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("GetPayAndWriteoffRecService")
public class GetPayAndWriteoffRecService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetPayAndWriteoffRecService.class);

    private static final String PARAM_OUT_STRSTARTDATE = "strStartDate";
    private static final String PARAM_OUT_STRENDDATE = "strEndDate";
    private static final String PARAM_QUERYMONTH = "queryMonth";

    @Autowired
    private PhoneInfoService phoneInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, PARAM_QUERYMONTH);
            } else {
                checkParams(jsonObject, PARAM_QUERYMONTH,
                        AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
            }
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG,
                    e.getMessage());
            return message;
        }
        Date date = MyDateUtil.getDateFromStringFormat(jsonObject.getString(PARAM_QUERYMONTH),
                MyDateUtil.FormatPattern.YYYYMM.getFormatPattern());
        if (null == date) {
            logger.error(LogHelper._FUNC_() + jsonObject.getString(PARAM_QUERYMONTH) + "时间转化失败！");
            message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        String strStartDate = jsonObject.getString(PARAM_QUERYMONTH) + "01";
        String strEndDate = MyDateUtil.getMonthLastDateStringFromDate(date,
                MyDateUtil.FormatPattern.YYYYMMDD.getFormatPattern());
        String phone;
        try {
            phone = getPhoneFromSessionOrParam(jsonObject, source);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_LOGIN_FIRST_MSG);
            return message;
        }

        Map<String, Object> map = new HashMap<>(3);
        map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
        map.put(PARAM_OUT_STRSTARTDATE, strStartDate);
        map.put(PARAM_OUT_STRENDDATE, strEndDate);
        List<PayAndWriteoffRec> list = phoneInfoService.getPayAndWriteoffRec(map);
        if (null == list) {
            logger.error(LogHelper._FUNC_() + "PayAndWriteoffRec is null!");
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }
        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_PAYANDWRITEOFFRECLIST,
                JSONArray.parseArray(JSON.toJSONString(list, new MyPascalNameFilter())));
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
