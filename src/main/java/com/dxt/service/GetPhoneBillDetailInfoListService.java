package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.UserBillDetail;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyDateUtil;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("GetPhoneBillDetailInfoListService")
public class GetPhoneBillDetailInfoListService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetPhoneBillDetailInfoListService.class);

    private static final String PARAM_BILLINGCYCLE = "billingCycle";
    private static final String PARAM_QUERYMONTH = "queryMonth";
    private static final String PARAM_QUERYNUM = "queryNum";

    @Autowired
    private PhoneInfoService phoneInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, PARAM_QUERYNUM);
            } else {
                checkParams(jsonObject, PARAM_QUERYNUM,
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
        JSONArray jsonArray = new JSONArray();

        Integer queryNum = Integer.valueOf(jsonObject.getString(PARAM_QUERYNUM));
        Date date = new Date();
        int i = 0;
        while (i < queryNum) {
            String strBillingCycle = MyDateUtil.getMonthStringFromDate(date,
                    MyDateUtil.FormatPattern.YYYYMM.getFormatPattern(), 0 - i);
            Map<String, Object> map = new HashMap<>(2);
            map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
            map.put(PARAM_BILLINGCYCLE, strBillingCycle);
            UserBillDetail userBillDetail = phoneInfoService.getUserUserBillDetail(map);
            JSONObject retObject = new JSONObject();
            retObject.put(PARAM_QUERYMONTH, MyDateUtil.getMonthStringFromDate(date,
                    MyDateUtil.FormatPattern.MMYYYY.getFormatPattern(), 0 - i));
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_MONTHBILLINFO,userBillDetail);
            jsonArray.add(retObject);
            i++;
        }
        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_MONTHBILLINFOLIST, jsonArray);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
