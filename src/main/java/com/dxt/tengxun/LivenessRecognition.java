package com.dxt.tengxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.dao.TxyLivenessRecognitionLogDao;
import com.dxt.message.ReponseMessage;
import com.dxt.service.CacheManager;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.LivenessRecognitionRequest;
import com.tencentcloudapi.faceid.v20180301.models.LivenessRecognitionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class LivenessRecognition
{
    @Autowired
    CacheManager cacheManager;
    @Autowired
    TxyLivenessRecognitionLogDao txyLivenessRecognitionLogDao;
    public ReponseMessage checkLive(String name, String idCard,String videoBase64,String plat){
        ReponseMessage reponseMessage = new ReponseMessage();
        String result = "";
        Map<String,String> map = new HashMap<>();
        try{
            Credential cred = new Credential(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETID),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETKEY));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("faceid.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
            String params = "{\"IdCard\":"+idCard+",\"Name\":"+name+",\"VideoBase64\":"+videoBase64+",\"LivenessType\":\"SILENT\"}";
            LivenessRecognitionRequest req = LivenessRecognitionRequest.fromJsonString(params, LivenessRecognitionRequest.class);
            LivenessRecognitionResponse resp = client.LivenessRecognition(req);
            result = LivenessRecognitionRequest.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG,
                    "调用腾讯云人脸核身接口报错:"+e.getMessage());
            return reponseMessage;
        }
        if(!result.isEmpty()){
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject jsonErrorObject = jsonObject.getJSONObject("Error");
            String requestId = jsonObject.getString("RequestId");
            map.put("responseStr",result);
            map.put("plat",plat);
            map.put("txyOrderId",requestId);
            if(jsonErrorObject != null){
                String code = jsonErrorObject.getString("Code");
                String message = jsonErrorObject.getString("Message");
                //添加人脸核身校验日志
                map.put("resCode",code);
                map.put("resMsg",message);
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        message);
            }else{
                String resultCode = jsonObject.getString("Result");
                String description = jsonObject.getString("Description");
                map.put("resCode",resultCode);
                map.put("resMsg",description);
//                String bestFrameBase64 = jsonObject.getString("BestFrameBase64");
                String sim = jsonObject.getString("Sim");
                if("Success".equals(resultCode)){
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,
                            description,jsonObject);
                }else{
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                            description);
                }
            }
            txyLivenessRecognitionLogDao.insertTxyLivenessRecognitionLog(map);
        }else{
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG);
        }
        return reponseMessage;
    }

}
