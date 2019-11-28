package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import com.dxt.model.TopUpEntity;
import com.dxt.util.Top_Up_Utils;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service("TopUpService")
public class TopUpService extends IBaseBusiService {
    private  static  final  String  PARAM_REQSERIAL="reqSerial";
    private  static  final  String  PARAM_DESTCODE="destCode";
    private  static  final  String  PARAM_FEEAMOUNT="feeAmount";

    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, e.getMessage());
            return reponseMessage;
        }


        Top_Up_Utils top_up_utils=new Top_Up_Utils();
        TopUpEntity topUpEntity=new TopUpEntity();
        topUpEntity.setDestCode(jsonObject.getString(PARAM_DESTCODE));
        topUpEntity.setFeeAmount(jsonObject.getString(PARAM_FEEAMOUNT));
        topUpEntity.setReqSerial(jsonObject.getString(PARAM_REQSERIAL));
        try {
            String httpsResponse = top_up_utils.getHttpsResponse(topUpEntity);
            if (httpsResponse.equals("0000")){
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK, AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, "充值成功");
            }else {
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,"充值失败");
            }

        } catch (IOException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.SYS_ERROE, e.getMessage());
            return reponseMessage;
        }


        return reponseMessage;
    }

    private void checkParms(JSONObject jsonObject) throws MyBusiException {
        if("".equals(jsonObject.getString(PARAM_REQSERIAL)) || "".equals(jsonObject.getString(PARAM_DESTCODE)) || "".equals(jsonObject.getString(PARAM_FEEAMOUNT))){
            throw  new  MyBusiException("参数不能为空");
        }

        if (jsonObject.getString(PARAM_REQSERIAL).length()>50){
            throw  new MyBusiException("充值请求流水号长度不能大于50");
        }
        if (jsonObject.getString(PARAM_DESTCODE).length()>30){
            throw  new  MyBusiException("被充值的业务码长度不能大于30");
        }
        if (jsonObject.getString(PARAM_FEEAMOUNT).length()>20){
            throw  new MyBusiException("充值金额长度不能大于20位");
        }

    }
}
