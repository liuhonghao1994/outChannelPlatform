package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.VerificationIdCardDao;
import com.dxt.message.ReponseMessage;
import com.dxt.util.CommonUtils;
import com.dxt.util.IdCardVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("VerificationIdCardService")
public class VerificationIdCardService extends IBaseBusiService{
    @Autowired
    private VerificationIdCardDao verificationIdCardDao;


    private static final String PARAM_IdCARDNAME= "Idcardname";
    private static final String PARAM_IDCARDNUMBER= "Idcardnumber";
    private static final String PARAM_IDCARDADDRESS= "Idcardaddress";
    private static final String PARAM_IDCARDVALIDITY= "Idcardvalidity";
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
            map.put("in_custcertcode",jsonObject.getString(PARAM_IDCARDNUMBER));
            map.put("in_openid",jsonObject.getString(PARAM_IDCARDNUMBER));

            //先调用存贮过程，成功直接返回结果，然后调用国政通，失败直接返回用户失败结果
            verificationIdCardDao.verificationIdCard(map);
            JSONObject retObject = new JSONObject();
            retObject.put("VO_RETURN_CODE",map.get("vo_return_cod"));
            retObject.put("VO_RETURN_MESSAGE",map.get("vo_return_message"));
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
            //
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }

    private void checkParmas(JSONObject jsonObject) throws MyBusiException{
        if ("".equals(jsonObject.getString(PARAM_IDCARDNUMBER)) || jsonObject.getString(PARAM_IDCARDNUMBER).length()!=18) {
            throw new MyBusiException("身份证号码有误");
        }

        boolean validDate = CommonUtils.isValidDate(jsonObject.getString(PARAM_IDCARDVALIDITY));
        if (validDate==false){
            throw  new  MyBusiException("日期格式不符合规范！");
        }

        //比较证件有效期
        String format = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String format1 = sdf.format(new Date());
        if (Integer.valueOf(format1)>Integer.valueOf(jsonObject.getString(PARAM_IDCARDVALIDITY)))
        {
            throw  new MyBusiException("证件已过期");
        }


        if (IdCardVerification.IDCardValidate(jsonObject.getString(PARAM_IDCARDNUMBER))==false){
            throw  new  MyBusiException("证件号码有误！");
        }
    }
}
