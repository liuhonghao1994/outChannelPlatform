package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.CardNumberMatchingDao;
import com.dxt.message.ReponseMessage;
import com.dxt.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("CardNumberMatchingService")
public class CardNumberMatchingService extends IBaseBusiService {


    private static final String PARAM_PHONE= "PHONE";
    private static final String PARAM_ICCID_SN= "Iccid_sn";

    @Autowired
    private CardNumberMatchingDao cardNumberMatchingDao;
    @Override
    public ReponseMessage execute(String reqInfo, String source) {

        ReponseMessage responseMessage=new ReponseMessage();

        JSONObject jsonObject = JSON.parseObject(reqInfo);

        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return responseMessage;
        }


        try {
            Map<String,String> map=new HashMap<>();
            map.put(PARAM_PHONE,jsonObject.getString(PARAM_PHONE));
            map.put(PARAM_ICCID_SN,jsonObject.getString(PARAM_ICCID_SN));
            cardNumberMatchingDao.cardMatch(map);
            JSONObject retObject = new JSONObject();
            retObject.put("VO_RETURN_CODE",map.get("VO_RETURN_CODE"));
            retObject.put("VO_ICCID",map.get("VO_ICCID"));
            retObject.put("VO_RETURN_MESSAGE",map.get("VO_RETURN_MESSAGE"));
            responseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
        } catch (Exception e) {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return responseMessage;
        }

        return responseMessage;
    }

    private void checkParms(JSONObject jsonObject) throws MyBusiException {
            if (jsonObject.getString(PARAM_PHONE).equals("") || "".equals(jsonObject.getString(PARAM_ICCID_SN))){
                throw new MyBusiException("缺少请求参数");
            }else if (!CommonUtils.inValid(jsonObject.getString(PARAM_PHONE))){
                throw new MyBusiException("号码格式不正确");
            }else if (jsonObject.getString(PARAM_ICCID_SN).length()!=20){
                throw  new MyBusiException("iccid或sn位数不满足要求");
            }
    }
}
