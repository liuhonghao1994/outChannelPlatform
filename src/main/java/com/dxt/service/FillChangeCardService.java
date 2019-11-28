package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.FillChangeCardDao;
import com.dxt.message.ReponseMessage;
import com.dxt.util.CommonUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("FillChangeCardService")
public class FillChangeCardService extends IBaseBusiService{
    private static final String PARAM_NUMBER = "PHONE";
    private static final String PARAM_ICCID_SN= "Iccid_sn";
    @Autowired
    private FillChangeCardDao fillChangeCardDao;
    @Override
    public ReponseMessage execute(String reqInfo, String source) {

        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);

        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        try {

            String number = fillChangeCardDao.numberIsValid(jsonObject.getString(PARAM_NUMBER));
            if (number==null || TextUtils.isEmpty(number)){
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                        "您输入的号码不存在!");
            }else {
                Map<String,String> map=new HashMap<>();
                map.put("VI_BILL",jsonObject.getString(PARAM_NUMBER));
                map.put("VI_ICCID",jsonObject.getString(PARAM_ICCID_SN));
                fillChangeCardDao.fillOrChangeCard(map);
                JSONObject retObject = new JSONObject();
                retObject.put("VO_RETURN_CODE",map.get("VO_RETURN_CODE"));
                retObject.put("VO_RETURN_MESSAGE",map.get("VO_RETURN_MESSAGE"));
                retObject.put("VO_ICCID",map.get("VO_ICCID"));
                retObject.put("VO_ICCID_OLD",map.get("VO_ICCID_OLD"));
                retObject.put("VO_IMSI_OLD",map.get("VO_IMSI_OLD"));
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
            }
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        return reponseMessage;
    }

    private void checkParms(JSONObject jsonObject)   throws MyBusiException {
        if (TextUtils.isEmpty(jsonObject.getString(PARAM_NUMBER)) || TextUtils.isEmpty(jsonObject.getString(PARAM_ICCID_SN))){
            throw  new  MyBusiException("请求参数不完整");
        }else if (!CommonUtils.inValid(jsonObject.getString(PARAM_NUMBER))){
            throw  new  MyBusiException("手机号码格式不正确！");
        }else if (jsonObject.getString(PARAM_ICCID_SN).length()!=20){
            throw  new  MyBusiException("iccid或sn位数不正确");
        }

    }
}
