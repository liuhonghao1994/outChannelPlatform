package com.dxt.tengxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.dao.TxyOcrAnalyseCardLogDao;
import com.dxt.message.ReponseMessage;
import com.dxt.model.CustCertInfo;
import com.dxt.model.TxyOcrAnalyseCardLogBean;
import com.dxt.service.CacheManager;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IDCardOCR
{
    @Autowired
    CacheManager cacheManager;
    @Autowired
    CustCertInfo custCertInfo;
    @Autowired
    TxyOcrAnalyseCardLogDao txyOcrAnalyseCardLogDao;
    @Autowired
    TxyOcrAnalyseCardLogBean txyOcrAnalyseCardLogBean;
    public ReponseMessage idCardOCR(String imageBase64, String cardSide,String plat){
        ReponseMessage reponseMessage = new ReponseMessage();
        String result = "";
        JSONObject retObject = new JSONObject();
        try{
            Credential cred = new Credential(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETID),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETKEY));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);
            String params = "{\"ImageBase64\":"+imageBase64+",\"CardSide\":"+cardSide+"}";
            IDCardOCRRequest req = IDCardOCRRequest.fromJsonString(params, IDCardOCRRequest.class);
            IDCardOCRResponse resp = client.IDCardOCR(req);
            result = IDCardOCRRequest.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG,
                    "调用腾讯云身份证OCR信息识别接口报错:"+e.getMessage());
            return reponseMessage;
        }
        if(!result.isEmpty()){
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject jsonErrorObject = jsonObject.getJSONObject("Error");
            String requestId = jsonObject.getString("RequestId");
            //添加OCR证件识别日志
            txyOcrAnalyseCardLogBean.setTxyOrderId(requestId);
            txyOcrAnalyseCardLogBean.setCardType(cardSide);
            txyOcrAnalyseCardLogBean.setPlat(plat);
            txyOcrAnalyseCardLogBean.setResponseStr(jsonObject.toJSONString());
            txyOcrAnalyseCardLogDao.insertTxyOcrAnalyseCardLog(txyOcrAnalyseCardLogBean);
            if(jsonErrorObject != null){
                String code = jsonErrorObject.getString("Code");
                String message = jsonErrorObject.getString("Message");
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        message);
            }else{
                if("FRONT".equals(cardSide)){
                    custCertInfo.setSex(jsonObject.getString("Sex"));
                    custCertInfo.setNation(jsonObject.getString("Nation"));
                    custCertInfo.setBirthday(jsonObject.getString("Birth"));
                    custCertInfo.setIdCode(jsonObject.getString("IdNum"));
                    custCertInfo.setName(jsonObject.getString("Name"));
                    custCertInfo.setAddress(jsonObject.getString("Address"));
                }else{
                    custCertInfo.setAuthority(jsonObject.getString("Authority"));
                    custCertInfo.setValidDate(jsonObject.getString("ValidDate"));
                }
                custCertInfo.setId(requestId);
                retObject.put("custCertInfo",custCertInfo);
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
            }
        }else{
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG,result);
        }
        return reponseMessage;

    }

    public static void main(String [] args) {
        try{

            Credential cred = new Credential("AKIDd17xDD1OaMvEI6BwxiEYdVCftsf145i4", "5xZZOxGJ6dyPaU9Xh9PPCjzy5xYswkJ1");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-beijing", clientProfile);

            String params = "{\"ImageBase64\":\"1111111\",\"ImageUrl\":\"1122\",\"CardSide\":\"22222\",\"Config\":\"12222212\"}";
            IDCardOCRRequest req = IDCardOCRRequest.fromJsonString(params, IDCardOCRRequest.class);

            IDCardOCRResponse resp = client.IDCardOCR(req);

            System.out.println(IDCardOCRRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }

}
