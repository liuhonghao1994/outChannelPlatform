package com.dxt.tengxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.dao.CustCertInfoDao;
import com.dxt.dao.IdCardCheckLogDao;
import com.dxt.message.ReponseMessage;
import com.dxt.model.CustCertInfo;
import com.dxt.model.IdCardCheckLogBean;
import com.dxt.service.CacheManager;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.faceid.v20180301.FaceidClient;

import com.tencentcloudapi.faceid.v20180301.models.IdCardVerificationRequest;
import com.tencentcloudapi.faceid.v20180301.models.IdCardVerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdCardVerification
{
    @Autowired
    CacheManager cacheManager;
    @Autowired
    CustCertInfoDao custCertInfoDao;
    @Autowired
    CustCertInfo custCertInfo;
    @Autowired
    IdCardCheckLogBean idCardCheckLogBean;
    @Autowired
    IdCardCheckLogDao idCardCheckLogDao;
    public ReponseMessage checkIdCard(String name, String idCard){
        ReponseMessage reponseMessage = new ReponseMessage();
        String result = "";
        try{
            Credential cred = new Credential(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETID),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETKEY));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("faceid.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
            String params = "{\"IdCard\":"+idCard+",\"Name\":"+name+"}";
            IdCardVerificationRequest req = IdCardVerificationRequest.fromJsonString(params, IdCardVerificationRequest.class);
            IdCardVerificationResponse resp = client.IdCardVerification(req);
            result = IdCardVerificationRequest.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG,
                    "调用腾讯云身份信息认证接口报错:"+e.getMessage());
            idCardCheckLogBean.setResultCode("9999");
            idCardCheckLogBean.setResultMsg("调用腾讯云接口报错:"+e.getMessage());
            idCardCheckLogBean.setResultRequestId("9999");
            return reponseMessage;
        }
        if(!result.isEmpty()){
            JSONObject jsonObject = JSON.parseObject(result);
            String resultCode = jsonObject.getString("Result");
            String resultMsg = jsonObject.getString("Description");
            String resultRequestId = jsonObject.getString("RequestId");
            if("0".equals(resultCode)){
                custCertInfo.setId(resultRequestId);
                custCertInfo.setIdCode(idCard);
                custCertInfo.setName(name);
                //添加证件库信息
                custCertInfoDao.insertCustCertInfo(custCertInfo);
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
            }else{
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        resultMsg);
            }
            idCardCheckLogBean.setResultCode(resultCode);
            idCardCheckLogBean.setResultMsg(resultMsg);
            idCardCheckLogBean.setResultRequestId(resultRequestId);
        }else{
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG,result);
            idCardCheckLogBean.setResultCode("1000");
            idCardCheckLogBean.setResultMsg("未获取到腾讯云返回的报文");
            idCardCheckLogBean.setResultRequestId("0");
        }
        idCardCheckLogBean.setIdCard(idCard);
        idCardCheckLogBean.setName(name);
        idCardCheckLogBean.setPlat("TXY");
        idCardCheckLogDao.insertIdCardCheckLog(idCardCheckLogBean);
        return reponseMessage;
    }

}
