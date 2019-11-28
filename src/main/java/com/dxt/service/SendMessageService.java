package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;

import com.dxt.common.MyBusiException;
import com.dxt.dao.SendMessageMapper;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dxt.util.CommonUtils.inValid;


@Service("SendMessageService")
public class SendMessageService extends IBaseBusiService {
    @Autowired
    private SendMessageMapper sendMessageMapper;
    private static final String PARAM_NUMBER = "PHONE";
    private static final String PARAM_MESSAGE = "msg";
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        // 业务参数校验
        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }
        try {
            sendMessageMapper.sendMessage(jsonObject.getString(PARAM_NUMBER),jsonObject.getString(PARAM_MESSAGE));
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG);
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }
    //参数校验
    private void checkParms(JSONObject jsonObject) throws MyBusiException{
        if (null == jsonObject.getString(PARAM_NUMBER) || "".equals(jsonObject.getString(PARAM_MESSAGE))) {
            throw new MyBusiException("参数不可为空！");
        }else if (!inValid(jsonObject.getString(PARAM_NUMBER) )){
            throw new MyBusiException("手机号码格式有误!");
        }else if (jsonObject.getString(PARAM_MESSAGE).length()>202){
            throw new MyBusiException("短信内容不应大于202字符");
        }
    }

}
