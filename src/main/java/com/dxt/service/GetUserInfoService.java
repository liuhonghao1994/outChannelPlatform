package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.UserInfoModle;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.UserInfoDao;
import com.dxt.message.ReponseMessage;
import com.dxt.util.Response;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.service.ResponseMessage;

@Service("GetUserInfoService")
public class GetUserInfoService extends IBaseBusiService {
    private final  static  String PARM_PHONE="phone";
    private ReponseMessage responseMessage;
    private JSONObject jsonObject;
    @Autowired
    private UserInfoDao userInfoDao;
    private UserInfoModle userInfoMes;

    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        responseMessage = new ReponseMessage();
        jsonObject = JSON.parseObject(reqInfo);
        //参数验证
        try {
            checkParms();
        } catch (MyBusiException e) {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,e.getMessage());
            return  responseMessage;
        }


        try {
            userInfoMes = userInfoDao.getUserInfoMes(jsonObject.getString(PARM_PHONE));
            if (userInfoMes==null){
                responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,"查询无信息");
            }else {
                responseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG, userInfoMes);

            }
        } catch (Exception e) {
            responseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING, AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,e.getMessage());
            return  responseMessage;
        }

        return responseMessage;
    }

    private void checkParms() throws MyBusiException {
            if (jsonObject.getString(PARM_PHONE)==null || "".equals(jsonObject.getString(PARM_PHONE))){
                throw  new  MyBusiException("入参不能为空");
            }
            if (jsonObject.getString(PARM_PHONE).length()!=11){
                throw  new  MyBusiException("电话号码格式错误");
            }
    }
}
