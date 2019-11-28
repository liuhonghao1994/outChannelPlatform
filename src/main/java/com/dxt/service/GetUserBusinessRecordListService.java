package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.BusiRecInfo;
import com.dxt.boss.service.PhoneInfoService;
import com.dxt.common.*;
import com.dxt.message.ReponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("GetUserBusinessRecordListService")
public class GetUserBusinessRecordListService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetUserBusinessRecordListService.class);

    private static final String PARAM_OUT_STRSTARTDATE = "strStartDate";
    private static final String PARAM_OUT_STRENDDATE = "strEndDate";
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
            String strStartDate = MyDateUtil.getMonthFirstDateStringFromDate(date,
                    MyDateUtil.FormatPattern.YYYYMMDD.getFormatPattern(), 0 - i);
            String strEndDate = MyDateUtil.getMonthLastDateStringFromDate(date,
                    MyDateUtil.FormatPattern.YYYYMMDD.getFormatPattern(), 0 - i);
            Map<String, Object> map = new HashMap<>(3);
            map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
            map.put(PARAM_OUT_STRSTARTDATE, strStartDate);
            map.put(PARAM_OUT_STRENDDATE, strEndDate);
            List<BusiRecInfo> list = phoneInfoService.getUserBusinessRecord(map);
            JSONObject retObject = new JSONObject();
            retObject.put(PARAM_QUERYMONTH, MyDateUtil.getMonthStringFromDate(date,
                    MyDateUtil.FormatPattern.MMYYYY.getFormatPattern(), 0 - i));
            retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_USERBUSINESSRECORDLIST,
                    JSONArray.parseArray(JSON.toJSONString(list, new MyPascalNameFilter())));
            jsonArray.add(retObject);
            i++;
        }
        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_MONTHUSERBUSINESSRECORDLIST, jsonArray);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
