package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.PhoneModle;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyPascalNameFilter;
import com.dxt.dao.AuditStatusQueryDao;
import com.dxt.message.ReponseMessage;
import com.dxt.model.AuditStatusBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AuditStatusQueryService")
public class AuditStatusQueryService  extends  IBaseBusiService{
    private static final String PARAM_PHONE= "phone";
    private ReponseMessage reponseMessage;
    private JSONObject jsonObject;
    @Autowired
    private AuditStatusQueryDao auditStatusQueryDao;
    private AuditStatusBean auditMes;

    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        reponseMessage = new ReponseMessage();
        jsonObject = JSON.parseObject(reqInfo);
        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_ERROR,e.getMessage());
            return  reponseMessage;
        }


        try {
            auditMes = auditStatusQueryDao.getAuditMes(jsonObject.getString(PARAM_PHONE));
            if (auditMes.getBill_id()==null || "".equals(auditMes.getBill_id())){
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,AppConstant.REPONSE_MSG.SYS_REQUEST_FAIL_MSG,"未查找到相关结果");
            }else {
                reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,auditMes);
            }


        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }


        return reponseMessage;
    }

    private void checkParms(JSONObject jsonObject) throws MyBusiException {

        if (jsonObject.getString(PARAM_PHONE)==null || "".equals(jsonObject.getString(PARAM_PHONE))){
            throw new MyBusiException("入参不能为空");
        }

        if (jsonObject.getString(PARAM_PHONE).length()!=11){
            throw new MyBusiException("号码格式不正确");
        }

    }
}
