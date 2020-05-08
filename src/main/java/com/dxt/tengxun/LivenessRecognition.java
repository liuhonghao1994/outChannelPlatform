package com.dxt.tengxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.FFmpegVideo;
import com.dxt.common.MyDateUtil;
import com.dxt.dao.TxyLivenessRecognitionLogDao;
import com.dxt.message.ReponseMessage;
import com.dxt.model.LivenessInfo;
import com.dxt.service.CacheManager;
import com.dxt.util.ImgErToFileUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.faceid.v20180301.FaceidClient;
import com.tencentcloudapi.faceid.v20180301.models.LivenessRecognitionRequest;
import com.tencentcloudapi.faceid.v20180301.models.LivenessRecognitionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class LivenessRecognition
{
    @Autowired
    CacheManager cacheManager;
    @Autowired
    TxyLivenessRecognitionLogDao txyLivenessRecognitionLogDao;
    @Autowired
    LivenessInfo livenessInfo;
    private String newImgPath = "/home/appserver/video/";
    public ReponseMessage checkLive(String name, String idCard,String videoBase64,String plat){
        ReponseMessage reponseMessage = new ReponseMessage();
        String result = "";
        Map<String,String> map = new HashMap<>();
        JSONObject retObject = new JSONObject();
        Integer videoSize = FFmpegVideo.imageSize(videoBase64);
        map.put("name",name);
        map.put("idCard",idCard);
        map.put("plat",plat);
        map.put("txyOrderId",String.valueOf(System.currentTimeMillis()));
        if(videoSize > 7*1024*1024){
            map.put("resCode","1000");
            map.put("resMsg","视频超过7M不可压缩识别");
            txyLivenessRecognitionLogDao.insertTxyLivenessRecognitionLog(map);
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    "视频超过7M不可压缩识别");
            return reponseMessage;
        }else{
            try{
                File filePath = new File(newImgPath);
                if(!filePath.isDirectory()){
                    filePath.mkdirs();
                }
                String dateStr = MyDateUtil.getDateStringFromDate(new Date(),MyDateUtil.FormatPattern.YYYYMMDDHHMMSS.getFormatPattern());
                String imgSource = newImgPath+idCard+"_"+dateStr+".mp4";
                String videoTarget = newImgPath+idCard+"_"+dateStr+"_z.mp4";
                boolean videoFileBool = ImgErToFileUtil.generateImage(videoBase64,imgSource);
                if(videoFileBool){
                    FFmpegVideo.toCompressFile_java(imgSource,videoTarget);
                    videoBase64 = ImgErToFileUtil.getImageStr(videoTarget);
                }else{
                    map.put("resCode","2000");
                    map.put("resMsg","视频base64串转File失败");
                    txyLivenessRecognitionLogDao.insertTxyLivenessRecognitionLog(map);
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                            "视频base64串转File失败。");
                    return reponseMessage;
                }
            }catch (Exception e){
                e.printStackTrace();
                map.put("resCode","3000");
                map.put("resMsg","视频base64串转File异常");
                txyLivenessRecognitionLogDao.insertTxyLivenessRecognitionLog(map);
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        idCard+",视频base64串转File失败。"+e.getMessage());
                return reponseMessage;
            }
        }
        try{
            Credential cred = new Credential(cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETID),
                    cacheManager.getSysConfigByCode(AppConstant.SYS_CONFIG_KEY.KEY_TX_CHECK_SECRETKEY));
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("faceid.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            FaceidClient client = new FaceidClient(cred, "ap-beijing", clientProfile);
            JSONObject paramsJson = new JSONObject();
            paramsJson.put("IdCard",idCard);
            paramsJson.put("Name",name);
            paramsJson.put("VideoBase64",videoBase64);
            paramsJson.put("LivenessType","SILENT");
            String params = paramsJson.toJSONString();
//            String params = "{\"IdCard\":"+idCard+",\"Name\":"+name+",\"VideoBase64\":"+videoBase64+",\"LivenessType\":\"SILENT\"}";
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
            map.put("plat",plat);
            map.put("txyOrderId",requestId);
            if(jsonErrorObject != null){
                String code = jsonErrorObject.getString("Code");
                String message = jsonErrorObject.getString("Message");
                //添加人脸核身校验日志
                map.put("resCode",code);
                map.put("resMsg",message);
                map.put("responseStr",result);
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        message,map);
            }else{
                String resultCode = jsonObject.getString("Result");
                String description = jsonObject.getString("Description");
                map.put("resCode",resultCode);
                map.put("resMsg",description);
                String bestFrameBase64 = jsonObject.getString("BestFrameBase64");
                String sim = jsonObject.getString("Sim");
                livenessInfo.setBestFrameBase64(bestFrameBase64);
                livenessInfo.setSim(sim);
                livenessInfo.setResCode(resultCode);
                livenessInfo.setResMsg(description);
                livenessInfo.setId(requestId);
                retObject.put(AppConstant.REQUEST_REPONSE_PARAM.PARAM_OUT_LIVENESSINFO,livenessInfo);
                if("Success".equals(resultCode)){
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,
                            description,retObject);
                }else{
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                            description,map);
                }
                jsonObject.remove("BestFrameBase64");
            }
            map.put("responseStr",jsonObject.toJSONString());
            txyLivenessRecognitionLogDao.insertTxyLivenessRecognitionLog(map);
        }else{
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    AppConstant.REPONSE_MSG.SYS_VERIFIY_ERROR_MSG);
        }
        return reponseMessage;
    }

}
