package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.UnuseBeanOfQueryPhone;
import com.dxt.boss.model.UseBeanOfQueryPhone;
import com.dxt.boss.service.UserInfoService;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.dao.QueryPhoneBySimDao;
import com.dxt.message.ReponseMessage;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;

@Service("QueryPhoneBySimService")
public class QueryPhoneBySimService extends IBaseBusiService {

    private static final String PARAM_ICCID_SN= "iccid_sn";
    @Autowired
    private QueryPhoneBySimDao queryPhoneBySimDao;



    private UnuseBeanOfQueryPhone unUseChartOfQueryPhone;

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
            JSONObject retObject = new JSONObject();
            unUseChartOfQueryPhone = queryPhoneBySimDao.getUnUseChartOfQueryPhone(jsonObject.getString(PARAM_ICCID_SN));
            if (unUseChartOfQueryPhone!=null){
                if (unUseChartOfQueryPhone.getIcc_id()!=null || unUseChartOfQueryPhone.getStatus()!=null){
                    retObject.put("PHONE","");
                    retObject.put("ICCID",unUseChartOfQueryPhone.getIcc_id());
                    retObject.put("STATUS",unUseChartOfQueryPhone.getStatus());
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
                }else {
                    UseBeanOfQueryPhone useChartOfQueryPhone = queryPhoneBySimDao.getUseChartOfQueryPhone(jsonObject.getString(PARAM_ICCID_SN));
                    retObject.put("PHONE",useChartOfQueryPhone.getBill_id());
                    retObject.put("ICCID",useChartOfQueryPhone.getIcc_id());
                    retObject.put("STATUS",useChartOfQueryPhone.getStatus());
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
                }
            }else {
                UseBeanOfQueryPhone useChartOfQueryPhoness = queryPhoneBySimDao.getUseChartOfQueryPhone(jsonObject.getString(PARAM_ICCID_SN));
                if (useChartOfQueryPhoness==null){
                    retObject.put("PHONE","");
                    retObject.put("ICCID","");
                    retObject.put("STATUS","");
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
                }else {
                    retObject.put("PHONE",useChartOfQueryPhoness.getBill_id());
                    retObject.put("ICCID",useChartOfQueryPhoness.getIcc_id());
                    retObject.put("STATUS",useChartOfQueryPhoness.getStatus());
                    reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
                }

            }

        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        return reponseMessage;
    }

    private void checkParmas(JSONObject jsonObject) throws  MyBusiException{
        if (TextUtils.isEmpty(jsonObject.getString(PARAM_ICCID_SN)) || "".equals(jsonObject.getString(PARAM_ICCID_SN)) || jsonObject.getString(PARAM_ICCID_SN).length()!=20){
            throw  new  MyBusiException("请输入您的20位iccid或sn号");
        }
    }
}
