package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.PhoneModle;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyPascalNameFilter;
import com.dxt.dao.FindAllPhoneNumberOfUserDao;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FindAllPhoneNumberOfUserService")
public class FindAllPhoneNumberOfUserService extends IBaseBusiService{
    private static String CUST_CERT_CODE="cust_cert_code";
    @Autowired
    private FindAllPhoneNumberOfUserDao findAllPhoneNumberOfUserDao;
    private JSONObject jsonObject;

    @Override
    public ReponseMessage execute(String reqInfo, String source) {
            ReponseMessage reponseMessage=new ReponseMessage();
            jsonObject = JSON.parseObject(reqInfo);
        try {
            checkPArms();
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }


        try {
            JSONObject retObject = new JSONObject();
            List<PhoneModle> allPhoneNumberOfUsers = findAllPhoneNumberOfUserDao.getAllPhoneNumberOfUser(jsonObject.getString(CUST_CERT_CODE));
            JSONArray objects = JSONArray.parseArray(JSON.toJSONString(allPhoneNumberOfUsers, new MyPascalNameFilter()));
            retObject.put("PhoneList", objects);
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);

        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }


        return reponseMessage;
    }

    private void checkPArms() throws MyBusiException {
        if ("".equals(jsonObject.getString(CUST_CERT_CODE)) || jsonObject.getString(CUST_CERT_CODE).length()!=18){
            throw  new MyBusiException("身份证号码格式有误!");
        }
    }
}
