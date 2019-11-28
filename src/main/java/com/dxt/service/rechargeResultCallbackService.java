package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import com.dxt.model.TopUpEntity;
import com.dxt.util.TopUpCallEntity;
import com.dxt.util.TopUpReturnCallBackUtils;
import com.dxt.util.Top_Up_Utils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("rechargeResultCallbackService")
public class rechargeResultCallbackService extends IBaseBusiService {
    private  static  final String PARM_TRANID="transactionId";
    private  static  final String PARM_PLATEID="platTransactionId";
    private  static  final String PARM_FREEAMOUNT="feeAmount";
    private  static  final String PARM_STATUS="status";
    private  static  final  String PARM_RETURNCODE="returnCode";
    private  static  final  String PARM_ERRORMSG="errorMsg";
    private  static  final String PARM_PHONE="destCode";
    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObjectMes = JSON.parseObject(reqInfo);
        System.out.println(jsonObjectMes.getString(PARM_TRANID)+"有没有数据啊");
        System.out.println(jsonObjectMes.getString(PARM_PLATEID)+"有没有数据啊");
        System.out.println(jsonObjectMes.getString(PARM_PHONE)+"有没有数据啊");
        System.out.println(jsonObjectMes.getString(PARM_FREEAMOUNT)+"有没有数据啊");
        System.out.println(jsonObjectMes.getString(PARM_STATUS)+"有没有数据啊");
        System.out.println(jsonObjectMes.getString(PARM_RETURNCODE)+"有没有数据啊");
        System.out.println(jsonObjectMes.getString(PARM_ERRORMSG)+"有没有数据啊");
        try {
            checkParmas(jsonObjectMes);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, e.getMessage());
            return reponseMessage;
        }
        TopUpReturnCallBackUtils topUpReturnCallBackUtils=new TopUpReturnCallBackUtils();
        TopUpCallEntity topUpCallEntity=new TopUpCallEntity();
        topUpCallEntity.setTransactionId(jsonObjectMes.getString(PARM_TRANID));
        topUpCallEntity.setPlatTransactionId(jsonObjectMes.getString(PARM_PLATEID));
        topUpCallEntity.setDestCode(jsonObjectMes.getString(PARM_PHONE));
        topUpCallEntity.setFeeAmount(jsonObjectMes.getString(PARM_FREEAMOUNT));
        topUpCallEntity.setStatus(jsonObjectMes.getString(PARM_STATUS));
        topUpCallEntity.setReturnCode(jsonObjectMes.getString(PARM_RETURNCODE));
        topUpCallEntity.setErrorMsg(jsonObjectMes.getString(PARM_ERRORMSG));

        try {
            String httpsResponse = topUpReturnCallBackUtils.getHttpsResponse(topUpCallEntity);
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, httpsResponse);
        } catch (IOException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, e.getMessage());
            return reponseMessage;
        }
        return reponseMessage;
    }

    private void checkParmas(JSONObject jsonObject) throws MyBusiException {
        if ("".equals(jsonObject.getString(PARM_TRANID)) || "".equals(jsonObject.getString(PARM_PLATEID)) || "".equals(jsonObject.getString(PARM_PHONE)) || "".equals(jsonObject.getString(PARM_FREEAMOUNT)) || "".equals(jsonObject.getString(PARM_STATUS)) || "".equals(jsonObject.getString(PARM_RETURNCODE)) || "".equals(jsonObject.getString(PARM_ERRORMSG))){
            throw  new  MyBusiException("输入参数不能为空");
        }
        if (jsonObject.getString(PARM_TRANID).length()>50){
            throw  new  MyBusiException("流水号长度不能大于50");
        }
        if (jsonObject.getString(PARM_PLATEID).length()>30){
            throw  new  MyBusiException("订单号长度不能大于30");
        }
       /* if (jsonObject.getString(PARM_DESTCODE).length()!=11){
            throw  new  MyBusiException("手机号码格式不正确");
        }
*/
        if (jsonObject.getString(PARM_STATUS).length()>10){
            throw  new  MyBusiException("状态码长度不能大于10");
        }
        if (jsonObject.getString(PARM_RETURNCODE).length()!=4){
            throw  new  MyBusiException("业务返回码有误");
        }
        if (jsonObject.getString(PARM_ERRORMSG).length()>200){
            throw  new  MyBusiException("错误提示长度过长");
        }
        /*if (jsonObject.getString(PARM_DESTCODE).length()!=11){
            throw  new  MyBusiException("电话号码格式不正确！");
        }*/

    }
}
