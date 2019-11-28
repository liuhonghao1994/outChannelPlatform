package com.dxt.boss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dxt.boss.common.BossConstant;
import com.dxt.boss.common.BossSocketClient;
import com.dxt.boss.model.*;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyDateUtil;
import com.dxt.service.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("PhoneInfoService")
public class PhoneInfoServiceImpl implements PhoneInfoService {

    private Logger logger = LoggerFactory.getLogger(PhoneInfoServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;
    private CallCDR callCDR;

    @Override
    public BalanceInfo getAccountBalance(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_ACCOUNT_BALANCE);
        Map<String, Object> busiParams = new HashMap<>(1);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new BalanceInfo();
        }
        try {
            BalanceInfo balanceInfo = JSON.parseObject(ret, new TypeReference<BalanceInfo>() {
            });
            balanceInfo.setCurrentDate(MyDateUtil.getDateStringFromDate(new Date(),
                    MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern()));
            return balanceInfo;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new BalanceInfo();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public RealTimeBillInfo getUserRealTimeBill(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_USER_REAL_TIME_BILL);
        Map<String, Object> busiParams = new HashMap<>(3);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new RealTimeBillInfo();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            RealTimeBillInfo realTimeBillInfo = JSON.parseObject(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_REALTIMEBILLINFO).toString(),
                    new TypeReference<RealTimeBillInfo>() {
                    });
            realTimeBillInfo.setCurrentDate(MyDateUtil.getDateStringFromDate(new Date(),
                    MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern()));
            return realTimeBillInfo;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new RealTimeBillInfo();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public UserBillDetail getUserUserBillDetail(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_USER_BILL_DETAIL);
        Map<String, Object> busiParams = new HashMap<>(3);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_BILLINGCYCLE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_BILLINGCYCLE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SUBJECTLEVEL, "3");
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new UserBillDetail();
        }
        try {
            UserBillDetail userBillDetail = JSON.parseObject(ret, new TypeReference<UserBillDetail>() {
            });
            return userBillDetail;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new UserBillDetail();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new UserBillDetail();
        }
//        return null;
    }

    @Override
    public CallCDR getCallCDR(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_CALL_CDR);
        Map<String, Object> busiParams = new HashMap<>(4);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRPHONE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRSTARTDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRSTARTDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRENDDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRENDDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_NQUERYMODE, "202");
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new CallCDR();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            callCDR = JSON.parseObject(retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_CALLCDR).toString(),
                    new TypeReference<CallCDR>() {
                    });
            return callCDR;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new CallCDR();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return  callCDR;
    }

    @Override
    public NetCDR getNetCDR(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_NET_CDR);
        Map<String, Object> busiParams = new HashMap<>(5);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRPHONE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRSTARTDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRSTARTDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRENDDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRENDDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_NQUERYMODE, "203");
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_ISMERGE, "0");
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new NetCDR();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            NetCDR netCDR = JSON.parseObject(retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_NETCDR).toString(),
                    new TypeReference<NetCDR>() {
                    });
            return netCDR;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new NetCDR();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public SmsMmsMainCDR getSmsAndMMSCDR(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_SMS_AND_MMS_CDR);
        Map<String, Object> busiParams = new HashMap<>(4);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRPHONE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRSTARTDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRSTARTDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STRENDDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRENDDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_NQUERYMODE, "204");
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new SmsMmsMainCDR();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            SmsMmsMainCDR smsMmsMainCDR = JSON.parseObject(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_SMSMMSMAINCDR).toString(),
                    new TypeReference<SmsMmsMainCDR>() {
                    });
            return smsMmsMainCDR;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new SmsMmsMainCDR();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public List<PayAndWriteoffRec> getPayAndWriteoffRec(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_PAY_AND_WRITEOFF_REC);
        Map<String, Object> busiParams = new HashMap<>(6);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_COMMON_PARAM.STR_OP_ID, BossConstant.UIP_COMMON_PARAM.PARAM_OP_ID);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_OPFLAG, "0");
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_BUSITYPE, "0");
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STARTDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRSTARTDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_ENDDATE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRENDDATE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<PayAndWriteoffRec> list = JSON.parseArray(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_PAYANDWRITEOFFREC).toString(),
                    PayAndWriteoffRec.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public List<BusiRecInfo> getUserBusinessRecord(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_USER_BUSINESS_RECORD);
        Map<String, Object> busiParams = new HashMap<>(3);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_STARTTIME,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRSTARTDATE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_ENDTIME,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_STRENDDATE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<BusiRecInfo> list = JSON.parseArray(retJson.get(
                    BossConstant.UIP_RESPONSE_PARAM.PARAM_BUSIRECINFO).toString(),
                    BusiRecInfo.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public List<FreeResourceInfo> getGetUserFreeRes(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_USER_FREE_RES);
        Map<String, Object> busiParams = new HashMap<>(2);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_QUERYTYPE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_QUERYTYPE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<FreeResourceInfo> list = JSON.parseArray(retJson.get(
                    BossConstant.UIP_RESPONSE_PARAM.PARAM_FREERESOURCEINFO).toString(),
                    FreeResourceInfo.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public List<FreeResourceInfo> getGetUserFreeResCom(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_USER_FREE_RES_COM);
        Map<String, Object> busiParams = new HashMap<>(2);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_QUERYTYPE,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_QUERYTYPE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<FreeResourceInfo> list = JSON.parseArray(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_FREERESOURCEINFO).toString(),
                    FreeResourceInfo.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public UsedResInfo getUserUsedRes(Map<String, Object> params) {
        String url = cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_QUERY_USED_RES_URL);
        if (null == url) {
            logger.error(LogHelper._FUNC_() + AppConstant.SYS_CONFIG_KEY.KEY_QUERY_USED_RES_URL + "没有配置");
            return null;
        }
        url += params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String ret = new RestTemplate().exchange(url, HttpMethod.GET, entity, String.class).getBody();
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            UsedResInfo usedResInfo = JSON.parseObject(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_RESULT).toString(), UsedResInfo.class);
            return usedResInfo;
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return null;
        }
    }

    @Override
    public List<OfferInfo> getUserSpecOffer(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_USER_SPEC_OFFER);
        Map<String, Object> busiParams = new HashMap<>(1);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<OfferInfo> list = JSON.parseArray(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERINFO).toString(),
                    OfferInfo.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public MainOfferInfo getMainOfferInfo(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME, BossConstant.UIP_API.OI_GET_USER_MAIN_OFFER);
        Map<String, Object> busiParams = new HashMap<>(1);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new MainOfferInfo();
        }
        try {
            MainOfferInfo mainOfferInfo = JSON.parseObject(ret, MainOfferInfo.class);
            return mainOfferInfo;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new MainOfferInfo();
            }
            e.printStackTrace();
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public List<OfferInfo> getUserAvailMainOffer(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_USER_AVAIL_MAIN_OFFER);
        Map<String, Object> busiParams = new HashMap<>(1);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<OfferInfo> list = JSON.parseArray(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_AVAILOFFERS).toString(),
                    OfferInfo.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public List<OfferInfo> getUserAvailVasOffer(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_USER_AVAIL_VAS_OFFER);
        Map<String, Object> busiParams = new HashMap<>(2);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_BUSITYPE, "222000000002");
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return new ArrayList<>();
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<OfferInfo> list = JSON.parseArray(
                    retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERINFO).toString(),
                    OfferInfo.class);
            return list;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return new ArrayList<>();
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public String getCarrierOperator(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>(2);
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_QUERY_CARRIER_OPERATOR);
        Map<String, Object> busiParams = new HashMap<>(1);
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = null;
        try {
            ret = BossSocketClient.execute(reqParams);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return "";
        }
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            String network = retJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_OPERATORCODE).toString();
            return network;
        } catch (Exception e) {
            if (BossConstant.UIP_COMMON_PARAM.STR_UIP_RET_CODE_1045.equals(ret)) {
                return "";
            }
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }


    @Override
    public String getUserHisOwedBill(Map<String, Object> params) {
        return null;
    }
}
