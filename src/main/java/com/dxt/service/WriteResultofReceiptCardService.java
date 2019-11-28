package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.WriteResultofReceiptCardDao;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service("WriteResultofReceiptCardService")
public class WriteResultofReceiptCardService extends IBaseBusiService {

    private static final String PARAM_ICCID= "ICCID";
    private static final String PARAM_IMSI= "IMSI";
    private static final String PARAM_PHONE= "PHONE";
    private static final String PARAM_CODE= "code";
    private static final String PARAM_MESSAGE= "message";
    @Autowired
    private WriteResultofReceiptCardDao writeResultofReceiptCardDao;
    @Override
    public ReponseMessage execute(String reqInfo, String source) {

        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            checkParmas(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        try {
            Map<String,String> map=new HashMap<>();
            map.put(PARAM_PHONE,jsonObject.getString(PARAM_PHONE));
            map.put(PARAM_ICCID,jsonObject.getString(PARAM_ICCID));
            map.put(PARAM_IMSI,jsonObject.getString(PARAM_IMSI));
            writeResultofReceiptCardDao.getWriteResultInfo(map);
            JSONObject retObject = new JSONObject();
            retObject.put("VO_RETURN_CODE",map.get("VO_RETURN_CODE"));
            retObject.put("VO_RETURN_MESSAGE",map.get("VO_RETURN_MESSAGE"));
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        return reponseMessage;
    }

    private void checkParmas(JSONObject jsonObject) throws MyBusiException {
        if ("".equals(jsonObject.getString(PARAM_ICCID)) || "".equals(jsonObject.getString(PARAM_MESSAGE)) ||"".equals(jsonObject.getString(PARAM_IMSI))  || "".equals(jsonObject.getString(PARAM_PHONE)) || "".equals(jsonObject.getString(PARAM_CODE)) ) {
            throw new MyBusiException("参数不可为空！");
        }else if (!inValid(jsonObject.getString(PARAM_PHONE) )){
            throw new MyBusiException("手机号码格式有误!");
        }else if (!inValidIccid(jsonObject.getString(PARAM_ICCID))){
            throw new MyBusiException("ICCID不正确");
        }else if (!jsonObject.getString(PARAM_CODE).equals("0000")){
            throw  new MyBusiException("无权调用此接口");
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
