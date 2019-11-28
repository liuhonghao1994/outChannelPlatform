package com.dxt.boss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dxt.boss.common.BossConstant;
import com.dxt.boss.common.BossSocketClient;
import com.dxt.boss.model.BalanceInfo;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("BusiProcessService")
public class BusiProcessServiceImpl implements BusiProcessService {

    private Logger logger = LoggerFactory.getLogger(BusiProcessServiceImpl.class);

    @Override
    public HashMap<String, Object> updateUserMainOffer(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_UPDATE_USER_MAIN_OFFER);
        Map<String, Object> busiParams = new HashMap<>(2);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_OFFERID,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_OFFERID));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = BossSocketClient.execute(reqParams);
        HashMap<String, Object> retMap = new HashMap<>();
        try {
            JSONObject jsonObject = JSON.parseObject(ret);
            String doneCode = jsonObject.getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_DONECODE);
            retMap.put(AppConstant.REPONSE_CODE.CODE, AppConstant.REPONSE_CODE.OK);
            retMap.put(AppConstant.REPONSE_MSG.MSG, doneCode);
            return retMap;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_3004.equals(ret)) {
                retMap.put(AppConstant.REPONSE_CODE.CODE, AppConstant.REPONSE_CODE.BUSI_ERROR);
                retMap.put(AppConstant.REPONSE_MSG.MSG, AppConstant.REPONSE_MSG.BUSI_ERROR_MSG);
                return retMap;
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public HashMap<String, Object> orderVasOffer(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_ORDER_VAS_OFFER);
        Map<String, Object> busiParams = new HashMap<>(2);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_VASOFFERINFO,
                params.get(BossConstant.UIP_REQUEST_PARAM.PARAM_VASOFFERINFO));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = BossSocketClient.execute(reqParams);
        HashMap<String, Object> retMap = new HashMap<>();
        try {
            JSONObject jsonObject = JSON.parseObject(ret);
            String doneCode = jsonObject.getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_DONECODE);
            retMap.put(AppConstant.REPONSE_CODE.CODE, AppConstant.REPONSE_CODE.OK);
            retMap.put(AppConstant.REPONSE_MSG.MSG, doneCode);
            return retMap;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_3004.equals(ret)) {
                retMap.put(AppConstant.REPONSE_CODE.CODE, AppConstant.REPONSE_CODE.BUSI_ERROR);
                retMap.put(AppConstant.REPONSE_MSG.MSG, AppConstant.REPONSE_MSG.BUSI_ERROR_MSG);
                return retMap;
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;

    }
}
