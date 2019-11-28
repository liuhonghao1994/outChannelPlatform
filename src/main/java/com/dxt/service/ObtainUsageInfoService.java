package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.ObtainUsageInfoDao;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ObtainUsageInfoService")
public class ObtainUsageInfoService extends IBaseBusiService {
    @Autowired
    private  ObtainUsageInfoDao obtainUsageInfoDao;
    private static final String PARAM_PHONE= "PHONE";
    private static final String PARAM_ICCID = "ICCID";
    @Override
    public ReponseMessage execute(String reqInfo, String source) {

        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        //业务参数校验
        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        try {
            Map<String,String> map=new HashMap<>();
            map.put(PARAM_PHONE,jsonObject.getString(PARAM_PHONE));
            map.put(PARAM_ICCID,jsonObject.getString(PARAM_ICCID));
            obtainUsageInfoDao.getUsageInfoMessages(map);
            JSONObject retObject = new JSONObject();
            retObject.put("ICCID",map.get("ICCID"));
            retObject.put("IMSI",map.get("IMSI"));
            retObject.put("PIN1",map.get("PIN1"));
            retObject.put("PIN2",map.get("PIN2"));
            retObject.put("PUK1",map.get("PUK1"));
            retObject.put("PUK2",map.get("PUK2"));
            retObject.put("SMS_CENTER",map.get("SMS_CENTER"));
            retObject.put("RETURN_CODE",map.get("RETURN_CODE"));
            retObject.put("RETURN_MESSAGE",map.get("RETURN_MESSAGE"));
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }

    private void checkParms(JSONObject jsonObject) throws MyBusiException {
        if (null == jsonObject.getString(PARAM_PHONE) || "".equals(jsonObject.getString(PARAM_ICCID))) {
            throw new MyBusiException("参数不可为空！");
        }else if (!inValid(jsonObject.getString(PARAM_PHONE))){
            throw  new  MyBusiException("电话号码格式不正确");
        }else if (!inValidIccid(jsonObject.getString(PARAM_ICCID))){
            throw  new  MyBusiException("ICCID不正确");
        }
    }
    //校验号码格式
    private boolean inValid(String number) {
        if(number.length()!=11){
           return  false;
        }
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        boolean matches = m.matches();
        if (!matches){
            return false;
        }
        return true;
    }

    //ICCID校验
    private boolean inValidIccid(String iccid){
        if (iccid.length()>20){
            return false;
        }
        return true;
    }
}
