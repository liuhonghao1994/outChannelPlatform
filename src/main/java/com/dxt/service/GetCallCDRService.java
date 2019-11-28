package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.CallCDR;
import com.dxt.boss.model.SdrCallDetail;
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

import java.text.DecimalFormat;
import java.util.*;

@Service("GetCallCDRService")
public class GetCallCDRService extends IBaseBusiService {
    
    private static final Logger logger = LoggerFactory.getLogger(GetCallCDRService.class);

    private static final String PARAM_STRSTARTDATE = "strStartDate";
    private static final String PARAM_STRENDDATE = "strEndDate";
    private static final String PARAM_OPPNUMBER = "oppNumber";

    @Autowired
    private PhoneInfoService phoneInfoService;
    
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
                checkParams(jsonObject, PARAM_STRSTARTDATE, PARAM_STRENDDATE);
            } else {
                checkParams(jsonObject, PARAM_STRSTARTDATE, PARAM_STRENDDATE,
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

        CallCDR callCDR = null;
        // Boss接口不支持跨月查询，所以这里需要进行拆分
        // 如果开始日期和结束日期是同一个月，则可以直接进行查询
        if (jsonObject.getString(PARAM_STRSTARTDATE).substring(0,6).equals(
                jsonObject.getString(PARAM_STRENDDATE).substring(0,6))) {
            Map<String, Object> map = new HashMap<>(3);
            map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
            map.put(PARAM_STRSTARTDATE, jsonObject.getString(PARAM_STRSTARTDATE));
            map.put(PARAM_STRENDDATE, jsonObject.getString(PARAM_STRENDDATE));
            callCDR = phoneInfoService.getCallCDR(map);
        } else {
            List<CallCDR> callCDRList = new ArrayList<>();
            List<String> queryMonthList = MyDateUtil.getYearMonthListByStartAndEndString(
                    jsonObject.getString(PARAM_STRSTARTDATE), jsonObject.getString(PARAM_STRENDDATE),
                    AppConstant.SYS_CONSTANT.SORT_DESC);
            if (null == queryMonthList) {
                message.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
                return message;
            }
            for (String queryMonth : queryMonthList) {
                Map<String, Object> map = new HashMap<>(3);
                map.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE, phone);
                // 第一个月，使用请求参数指定的日期
                if (queryMonth.equals(jsonObject.getString(PARAM_STRSTARTDATE).substring(0, 6))) {
                    map.put(PARAM_STRSTARTDATE, jsonObject.getString(PARAM_STRSTARTDATE));
                } else {
                    map.put(PARAM_STRSTARTDATE, queryMonth + "01");
                }
                String queryMonthLastDate = MyDateUtil.getMonthLastDateStringFromDate(
                        MyDateUtil.getDateFromStringWithFormatPattern(queryMonth,
                                MyDateUtil.FormatPattern.YYYYMM.getFormatPattern()),
                        MyDateUtil.FormatPattern.YYYYMMDD.getFormatPattern());
                // 最后一个月，使用请求参数指定的日期
                if (Long.parseLong(queryMonthLastDate) < Long.parseLong(jsonObject.getString(PARAM_STRENDDATE))) {
                    map.put(PARAM_STRENDDATE, queryMonthLastDate);
                } else {
                    map.put(PARAM_STRENDDATE, jsonObject.getString(PARAM_STRENDDATE));
                }
                CallCDR tmpCallCDR = phoneInfoService.getCallCDR(map);
                if (null != tmpCallCDR) {
                    callCDRList.add(tmpCallCDR);
                }
            }
            // 将callCDRList统一到一个CallCDR里
            for (CallCDR cdr : callCDRList) {
                // 过滤无值元素
                if (null == cdr || null == cdr.getSdrCallDetailList()) {
                    continue;
                }
                // 使用第一个有效元素进行初始化
                if (null == callCDR) {
                    callCDR = cdr;
                    continue;
                }
                callCDR.getSdrCallDetailList().addAll(cdr.getSdrCallDetailList());
                callCDR.setChangTuSumcharge(String.valueOf(Double.valueOf(callCDR.getChangTuSumcharge()) +
                        Double.valueOf(cdr.getChangTuSumcharge())));
                callCDR.setGuoJiLongCharge(String.valueOf(Double.valueOf(callCDR.getGuoJiLongCharge()) +
                        Double.valueOf(cdr.getGuoJiLongCharge())));
                callCDR.setGuojiRoamCharge(String.valueOf(Double.valueOf(callCDR.getGuojiRoamCharge()) +
                        Double.valueOf(cdr.getGuojiRoamCharge())));
                callCDR.setGuoNeiLongCharge(String.valueOf(Double.valueOf(callCDR.getGuoNeiLongCharge()) +
                        Double.valueOf(cdr.getGuoNeiLongCharge())));
                callCDR.setGuoNeiRoamCharge(String.valueOf(Double.valueOf(callCDR.getGuoNeiRoamCharge()) +
                        Double.valueOf(cdr.getGuoNeiRoamCharge())));
                callCDR.setLocalSumcharge(String.valueOf(Double.valueOf(callCDR.getLocalSumcharge()) +
                        Double.valueOf(cdr.getLocalSumcharge())));
                callCDR.setShiHuaCharge(String.valueOf(Double.valueOf(callCDR.getShiHuaCharge()) +
                        Double.valueOf(cdr.getShiHuaCharge())));
                callCDR.setManYouSumCharge(String.valueOf(Double.valueOf(callCDR.getManYouSumCharge()) +
                        Double.valueOf(cdr.getManYouSumCharge())));
                callCDR.setSumCharge(String.valueOf(Double.valueOf(callCDR.getSumCharge()) +
                        Double.valueOf(cdr.getSumCharge())));
            }
        }
        if (null == callCDR) {
            logger.error(LogHelper._FUNC_() + "callCDR is null!");
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_ERROR_MSG);
            return message;
        }

        String oppNumber = jsonObject.getString(PARAM_OPPNUMBER);
        if (null != oppNumber && !"".equals(oppNumber)) {
            // 暂时使用接口查询，然后过滤，不直接查库
            Iterator<SdrCallDetail> iterator = callCDR.getSdrCallDetailList().iterator();
            boolean isExists = false;
            Double sumCharge = 0d;
            while (iterator.hasNext()) {
                SdrCallDetail detail = iterator.next();
                if (detail.getOppNumber().equals(oppNumber)) {
                    isExists = true;
                    sumCharge += Double.valueOf(detail.getCharge());
                } else {
                    iterator.remove();
                }
            }
            if (isExists) {
                DecimalFormat df = new DecimalFormat("#.00");
                callCDR.setSumCharge(df.format(sumCharge));
            } else {
                callCDR.setSumCharge(null);
            }
            callCDR.setChangTuSumcharge(null);
            callCDR.setGuoJiLongCharge(null);
            callCDR.setGuojiRoamCharge(null);
            callCDR.setGuoNeiLongCharge(null);
            callCDR.setGuoNeiRoamCharge(null);
            callCDR.setLocalSumcharge(null);
            callCDR.setShiHuaCharge(null);
            callCDR.setManYouSumCharge(null);
        }

        // 返回值
        JSONObject retObject = new JSONObject();
        retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_CALLCDR, callCDR);
        message.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, retObject);
        return message;
    }

}
