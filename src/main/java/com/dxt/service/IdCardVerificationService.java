package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.LogHelper;
import com.dxt.common.MyBusiException;
import com.dxt.dao.CustCertInfoDao;
import com.dxt.message.ReponseMessage;
import com.dxt.model.CustCertInfo;
import com.dxt.tengxun.IdCardVerification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IdCardVerificationService")
public class IdCardVerificationService extends IBaseBusiService {

    private static final Logger logger = LoggerFactory.getLogger(IdCardVerificationService.class);

    @Autowired
    private IdCardVerification idCardVerification;
    @Autowired
    CustCertInfoDao custCertInfoDao;
    @Autowired
    CustCertInfo custCertInfo;
    @Autowired
    CacheManager cacheManager;
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        logger.debug(LogHelper._FUNC_START_());
        ReponseMessage message = new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        JSONObject retObject = new JSONObject();
        try {
            checkParams(jsonObject,AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_NAME,
                    AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_CERTCODE);
        } catch (MyBusiException e) {
            message.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, AppConstant.REPONSE_MSG.SYS_PARAM_ERROR_MSG);
            return message;
        }
        //先从本地证件库检验
        custCertInfo = custCertInfoDao.getCustInfoByIdCodeAndName(jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_NAME),
                jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_CERTCODE));
        if(custCertInfo!= null){
            retObject.put("resultCode","0");
            retObject.put("resultMsg","本地证件库校验成功");
            message.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            return message;
        }else{
            custCertInfo = custCertInfoDao.getCustInfoByIdCode(jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_CERTCODE));
            if(custCertInfo != null){
                retObject.put("resultCode","-1");
                retObject.put("resultMsg","本地证件库姓名和证件校验不一致");
                message.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG,retObject);
                return message;
            }else{
                String platID = cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_IDCARD_CHECK_PLAT_INFO);
                if(platID != null && AppConstant.IDCARD_CHECK_PLAT_INFO.VALUE_TXY.equals(platID)){
                    //走腾讯校验
                    return idCardVerification.checkIdCard(jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_NAME),
                            jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_CERTCODE));
                }else{
                    //不走校验
                    retObject.put("resultCode","0");
                    retObject.put("resultMsg","配置原因，未走检验程序");
                    message.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
                    return message;
                }

            }
        }
    }



}
