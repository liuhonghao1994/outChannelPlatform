package com.dxt.boss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.common.BossConstant;
import com.dxt.boss.common.BossSocketClient;
import com.dxt.boss.model.BaseCustInfo;
import com.dxt.boss.model.BaseUserInfo;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.dao.CommonDao;
import com.dxt.dao.FindIccidByPhone;
import com.dxt.model.AppUserBasicInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private FindIccidByPhone findIccidByPhone;
    @Override
    public boolean verifyUserPassword(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_VERIFY_USER_PASSWORD);
        Map<String, Object> busiParams = new HashMap<>();
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_PASSWORD,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PASSWORD));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String retVerifyUserPassword = BossSocketClient.execute(reqParams);
        if (null == retVerifyUserPassword || "".equals(retVerifyUserPassword)) {
            return false;
        }
        logger.debug(LogHelper._FUNC_() + "retVerifyUserPassword:" + retVerifyUserPassword);
        // 解析返回的业务参数
        JSONObject retVerifyUserPasswordJson = null;
        try {
            retVerifyUserPasswordJson = JSONObject.parseObject(retVerifyUserPassword);
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            return false;
        }
        // "RetInfo":{"IsWeakPwd":"0","RetryTimes":null}
        if (!BossConstant.UIP_RESPONSE_PARAM.PARAM_ISWEAKPWD_VALUE.equals(
                retVerifyUserPasswordJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_ISWEAKPWD)) ||
                null != retVerifyUserPasswordJson.get(BossConstant.UIP_RESPONSE_PARAM.PARAM_RETRYTIMES)) {
            logger.info(LogHelper._FUNC_() + "密码校验未通过！");
            return false;
        }
        return true;
    }

    @Override
    public AppUserBasicInfoBean getUserBasicInfo(Map<String, Object> params) {

        String iccid = findIccidByPhone.getIccid((String) params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));


        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_USER_ALL_INFO);
        Map<String, Object> busiParams = new HashMap<>();
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String info = BossSocketClient.execute(reqParams);

        AppUserBasicInfoBean appUserBasicInfoBean = new AppUserBasicInfoBean();
        appUserBasicInfoBean.setPhone(params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE).toString());
        appUserBasicInfoBean.setIccid(iccid);

        if (null != info && !"".equals(info)) {
            logger.debug(LogHelper._FUNC_() + "info:" + info);
            // 解析返回的业务参数
            try {
                JSONObject retGetUserAllInfoJson = JSONObject.parseObject(info);
                JSONObject userInfo = (JSONObject) retGetUserAllInfoJson.get(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_USERINFO);
                appUserBasicInfoBean.setState(userInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_STATE));
                appUserBasicInfoBean.setStateDesc(commonService.getStateDesc(
                        userInfo.getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_STATE)));
                appUserBasicInfoBean.setOsStatus(userInfo.getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_OSSTATUS));
                appUserBasicInfoBean.setOsStatusDesc(commonService.getOsStatusDesc(
                        userInfo.getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_OSSTATUS)));
                appUserBasicInfoBean.setRegionId(userInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_REGIONID));
                appUserBasicInfoBean.setOfferId(userInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERID));
                appUserBasicInfoBean.setUserId(userInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_USERID));
                JSONObject acctInfo = (JSONObject) retGetUserAllInfoJson.get(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_ACCTINFO);
                appUserBasicInfoBean.setAccStatus(acctInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_ACCSTATUS));
                appUserBasicInfoBean.setAcctId(acctInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_ACCID));
                appUserBasicInfoBean.setAccStatusDesc(commonService.getAccStatusDesc(
                        acctInfo.getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_ACCSTATUS)));
                if (null == appUserBasicInfoBean.getRegionId() || "".equals(appUserBasicInfoBean.getRegionId())) {
                    appUserBasicInfoBean.setRegionId(acctInfo.getString(
                            BossConstant.UIP_RESPONSE_PARAM.PARAM_REGIONID));
                }
                JSONObject custInfo = (JSONObject) retGetUserAllInfoJson.get(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_CUSTINFO);
                appUserBasicInfoBean.setCustName(custInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_CUSTNAME));
                appUserBasicInfoBean.setCustCertType(custInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_CUSTCERTTYPE));
                appUserBasicInfoBean.setCustCertCode(custInfo.getString(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_CUSTCERTCODE));
                if (null == appUserBasicInfoBean.getRegionId() || "".equals(appUserBasicInfoBean.getRegionId())) {
                    appUserBasicInfoBean.setRegionId(custInfo.getString(
                            BossConstant.UIP_RESPONSE_PARAM.PARAM_REGIONID));
                }
                JSONArray offerInfo = (JSONArray) retGetUserAllInfoJson.get(
                        BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERINFO);
                for (Object object : offerInfo) {
                    if (BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERTYPE_OFFER_PLAN_GSM.equals(
                            ((JSONObject) object).getString(BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERTYPE))) {
                        appUserBasicInfoBean.setOfferName(((JSONObject) object).getString(
                                BossConstant.UIP_RESPONSE_PARAM.PARAM_OFFERNAME));
                        break;
                    }
                }
                if (null != appUserBasicInfoBean.getRegionId() && !"".equals(appUserBasicInfoBean.getRegionId())) {
                    appUserBasicInfoBean.setRegionName(commonService.getRegionName(appUserBasicInfoBean.getRegionId()));
                }
            } catch (Exception e) {
                logger.error(LogHelper._FUNC_EXCEPTION_(), e);
            }
        }
        String avatarUrl = getUserAvatarUrl(params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE).toString());
        if (null != avatarUrl) {
            appUserBasicInfoBean.setAvatarUrl(avatarUrl);
        }
        return appUserBasicInfoBean;
    }

    @Override
    public BaseUserInfo getBaseUserInfo(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_USER_BASE_INFO);
        Map<String, Object> busiParams = new HashMap<>();
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = BossSocketClient.execute(reqParams);
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            List<BaseUserInfo> list = JSON.parseArray(retJson.getString(
                    BossConstant.UIP_RESPONSE_PARAM.PARAM_USERINFO), BaseUserInfo.class);
            for (BaseUserInfo info : list) {
                if (info.getBillId().equals(params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE).toString())) {
                    return info;
                }
            }
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }

    @Override
    public String getUserAvatarUrl(String phone) {
        return commonDao.getAvatarUrlByPhone(phone);
    }

    @Override
    public boolean setUserAvatarUrl(String phone, String url) {
        String oldUrl = commonDao.getAvatarUrlByPhone(phone);
        try {
            if (null == oldUrl) {
                commonDao.insertAvatarUrl(phone, url);
            } else {
                commonDao.updateAvatarUrlByPhone(phone, url);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUserPassword(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_UPDATE_USER_PASSWORD);
        Map<String, Object> busiParams = new HashMap<>();
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_ISVERIFYOLDPWD, "0");
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_NEWPWD, params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_NEWPWD));
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_ISVERIFYCERTCARD, "0");
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_ISSMSNOTIFY, "1");
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = BossSocketClient.execute(reqParams);
        if (null == ret) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<String> getCustomerPhoneNumber(String custCertCode) {
        return commonDao.getAllPhoneListByCustCertCode(custCertCode);
    }

    @Override
    public BaseCustInfo getBaseCustInfo(Map<String, Object> params) {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                BossConstant.UIP_API.OI_GET_CUSTOMER_INFO);
        Map<String, Object> busiParams = new HashMap<>();
        busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,
                params.get(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE));
        reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
        String ret = BossSocketClient.execute(reqParams);
        logger.debug(LogHelper._FUNC_() + ret);
        try {
            JSONObject retJson = JSONObject.parseObject(ret);
            BaseCustInfo info = JSON.parseObject(retJson.getString(
                    BossConstant.UIP_RESPONSE_PARAM.PARAM_CUSTINFO), BaseCustInfo.class);
            return info;
        } catch (Exception e) {
            logger.error(LogHelper._FUNC_EXCEPTION_(), e);
        }
        return null;
    }
}
