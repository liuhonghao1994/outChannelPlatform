package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyPascalNameFilter;
import com.dxt.dao.NumberListByIdCardDao;
import com.dxt.message.ReponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NumberListByIdCardService")
public class NumberListByIdCardService  extends IBaseBusiService{
    private static final String ID_CARD= "card";
    @Autowired
    private NumberListByIdCardDao numberListByIdCardDao;
    private List<String> numberListByIdCardList;
    private JSONObject retObject;

    @Override
    public ReponseMessage execute(String reqInfo, String source) {
        ReponseMessage reponseMessage=new ReponseMessage();
        JSONObject jsonObject = JSON.parseObject(reqInfo);
        try {
            checkParms(jsonObject);
        } catch (MyBusiException e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }

        try {
            retObject = new JSONObject();
            numberListByIdCardList = numberListByIdCardDao.getNumberListByIdCard(jsonObject.getString(ID_CARD));
            JSONArray objects = JSONArray.parseArray(JSON.toJSONString(numberListByIdCardList, new MyPascalNameFilter()));
            retObject.put("NumberLists", objects);
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
        }catch (Exception e){
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }

    private void checkParms(JSONObject jsonObject) throws MyBusiException {
        if (jsonObject.getString(ID_CARD).length()!=18){
            throw  new MyBusiException("身份证号码格式错误!");
        }
        if (jsonObject.getString(ID_CARD)==""){
            throw new MyBusiException("证件号码不能为空");
        }

    }
}
