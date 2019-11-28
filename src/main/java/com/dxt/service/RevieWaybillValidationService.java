package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.RevieWaybillValidationDao;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RevieWaybillValidationService")
public class RevieWaybillValidationService extends IBaseBusiService {
    @Autowired
    private RevieWaybillValidationDao revieWaybillValidationDao;
    private static final String PARAM_PHONE= "phone";
    @Override
    public ReponseMessage execute(String reqInfo, String source) {

        ReponseMessage responseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            checkPArms(jsonObject);
        } catch (MyBusiException e) {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  responseMessage;
        }
        Integer count = revieWaybillValidationDao.getRevieWaybillValidation(jsonObject.getString(PARAM_PHONE));

        if (count>0){
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,"此号码不能提交");
        }else {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,"此号码可以提交");
        }

        return responseMessage;
    }

    private void checkPArms(JSONObject jsonObject) throws MyBusiException{
        if (jsonObject.getString(PARAM_PHONE).length()!=11){
            throw new MyBusiException("请输入正确格式的手机号码");
        }
        if ("".equals(jsonObject.getString(PARAM_PHONE))){
            throw new MyBusiException("手机号码不能为空");
        }
    }
}
