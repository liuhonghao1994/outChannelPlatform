package com.dxt.boss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.common.BossConstant;
import com.dxt.boss.common.BossSocketClient;
import com.dxt.common.AppConstant;

import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import com.dxt.service.IBaseBusiService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



@Service("ActivityOrderService")
public class ActivityOrderService extends IBaseBusiService {
    private static final String PARAM_PHONE= "phone";
    private static final String PARAM_OFFERID= "offerId";
    private static final String PARAM_OPERTYPE= "operType";
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
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_INTERFACE_NAME,
                    BossConstant.UIP_API.OI_ORDER_PROMOTION);
            Map<String, Object> busiParams = new HashMap<>();
            busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_SERVICENUM,jsonObject.getString(PARAM_PHONE));
            busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_OFFERID,jsonObject.getString(PARAM_OFFERID));
            busiParams.put(BossConstant.UIP_REQUEST_PARAM.PARAM_OPERTYPE,jsonObject.getString(PARAM_OPERTYPE));
            reqParams.put(BossConstant.UIP_COMMON_PARAM.STR_BUSI_PARAMS, busiParams);
            String execute = BossSocketClient.execute(reqParams);
            if (execute!=null){
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,"成功！");
            }else {
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,"失败！");
            }


        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }

    private void checkParms(JSONObject jsonObject) throws MyBusiException {
        if (null == jsonObject.getString(PARAM_PHONE) || "".equals(jsonObject.getString(PARAM_OFFERID)) || "".equals(jsonObject.get(PARAM_OPERTYPE))) {
            throw new MyBusiException("参数不可为空！");
        }else if (!inValid(jsonObject.getString(PARAM_PHONE))){
            throw  new  MyBusiException("电话号码格式不正确");
        }
    }

    //校验号码格式
    private boolean inValid(String number) {
        if(number.length()!=11){
            return  false;
        }
        return true;
    }
}
