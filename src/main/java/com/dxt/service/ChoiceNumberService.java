package com.dxt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dxt.boss.model.ChoiceNumberNewBean;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.common.MyPascalNameFilter;
import com.dxt.dao.ChoiceNumberDao;
import com.dxt.message.ReponseMessage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ChoiceNumberService")
public class ChoiceNumberService  extends IBaseBusiService{
    private static final String PARAM_NET= "net";
    private static final String PARAM_REGION_ID= "region_id";
    private static final String PARAM_RES_LEVEL= "res_level";
    private static final String PARAM_PATTER_DEF_ID= "pattern_def_id";
    //仓库
    private static final String PARAM_RESTORED_ID= "res_store_id";
    private static final String PARAM_HRL= "hrl";
    private static final String PARAM_PAGENUMBRT="pagenumber";
    private static final String PARAM_PAGESIZE="pagesize";

    //匹配模式

    private static final String PARAM_MATCHPATTERN="match_pattern";
    private static final String PARAM_MATCHCOUNT="match_count";
    @Autowired
    private ChoiceNumberDao choiceNumberDao;
    private String net;
    private String region_id;
    private String res_level;
    private String pattern_def_id;
    private String res_store_id;
    private String hrl;
    private int page;
    private int pagesize;

    private String match_count;
    private int match_pattern;


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
/*
            ,jsonObject.getString(PARAM_REGION_ID),jsonObject.getString(PARAM_RES_LEVEL),jsonObject.getString(PARAM_PATTER_DEF_ID),jsonObject.getString(PARAM_RESTORED_ID),jsonObject.getString(PARAM_NET)
*/
            JSONObject retObject = new JSONObject();
            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_NET))){
                net = jsonObject.getString(PARAM_NET);
            }else {
                net=null;
            }

            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_REGION_ID))){
                region_id = jsonObject.getString(PARAM_REGION_ID);
            }else {
                region_id=null;
            }


            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_RES_LEVEL))){
                res_level = jsonObject.getString(PARAM_RES_LEVEL);
            }else {
                res_level=null;
            }

            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_PATTER_DEF_ID))){
                pattern_def_id = jsonObject.getString(PARAM_PATTER_DEF_ID);
            }else {
                pattern_def_id=null;
            }

            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_RESTORED_ID))){
                res_store_id = jsonObject.getString(PARAM_RESTORED_ID);
            }else {
                res_store_id=null;
            }

            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_HRL))){
                hrl = jsonObject.getString(PARAM_HRL);
            }else {
                hrl=null;
            }

            if (
                    TextUtils.isEmpty(jsonObject.getString(PARAM_PAGENUMBRT))){
                page=1;
            }else {
                page=Integer.parseInt(jsonObject.getString(PARAM_PAGENUMBRT));
            }

            if (TextUtils.isEmpty(jsonObject.getString(PARAM_PAGESIZE))){
                pagesize=10;
            }else {
                pagesize=Integer.parseInt(jsonObject.getString(PARAM_PAGESIZE));
            }

            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_MATCHPATTERN))){
                match_pattern=jsonObject.getInteger(PARAM_MATCHPATTERN);
            }else {
                match_pattern=0;
            }

            if (!TextUtils.isEmpty(jsonObject.getString(PARAM_MATCHCOUNT))){
                match_count=jsonObject.getString(PARAM_MATCHCOUNT);
            }else {
                match_count=null;
            }


            PageInfo<ChoiceNumberNewBean> alls = findAll(page, pagesize);
            List<ChoiceNumberNewBean> lists = alls.getList();
            JSONArray objects = JSONArray.parseArray(JSON.toJSONString(lists, new MyPascalNameFilter()));
            retObject.put("choiceNumberLists", objects);
            retObject.put("totalsize", alls.getTotal());
            retObject.put("page",alls.getPageNum());
            retObject.put("pagesize",alls.getPageSize());
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.OK,AppConstant.REPONSE_MSG.SYS_REQUEST_OK_MSG,retObject);
        } catch (Exception e) {
            reponseMessage.setMsg(AppConstant.REPONSE_CODE.BUSI_WARNING,
                    e.getMessage());
            return  reponseMessage;
        }
        return reponseMessage;
    }

    private void checkParmas(JSONObject jsonObject) throws MyBusiException {
        if (!TextUtils.isEmpty(jsonObject.getString(PARAM_RES_LEVEL))){
            if (!(jsonObject.getString(PARAM_RES_LEVEL).equals("1")) && !(jsonObject.getString(PARAM_RES_LEVEL).equals("2")) && !(jsonObject.getString(PARAM_RES_LEVEL).equals("3"))&& !(jsonObject.getString(PARAM_RES_LEVEL).equals("4"))&& !(jsonObject.getString(PARAM_RES_LEVEL).equals("5")) && !(jsonObject.getString(PARAM_RES_LEVEL).equals("6")) && !(jsonObject.getString(PARAM_RES_LEVEL).equals("7"))&& !(jsonObject.getString(PARAM_RES_LEVEL).equals("8"))&& !(jsonObject.getString(PARAM_RES_LEVEL).equals("9")) && !(jsonObject.getString(PARAM_RES_LEVEL).equals("10"))){
                throw new MyBusiException("号码级别不符合规范");
            }
        }

        if (!TextUtils.isEmpty(jsonObject.getString(PARAM_HRL))){
            if (jsonObject.getString(PARAM_HRL).length()<3){
                throw  new MyBusiException("号段长度不能低于四位");
            }
        }




        if (!TextUtils.isEmpty(jsonObject.getString(PARAM_HRL))){
            if (jsonObject.getString(PARAM_HRL).length()<3){
                throw  new MyBusiException("号段长度不能低于三位");
            }
        }






    }


    public PageInfo<ChoiceNumberNewBean> findAll(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<ChoiceNumberNewBean> all = choiceNumberDao.getChoiceNumber(net,region_id,res_level,pattern_def_id,res_store_id,hrl,match_pattern,match_count);
        PageInfo<ChoiceNumberNewBean>  pageInfo=new PageInfo<ChoiceNumberNewBean>(all);
        return  pageInfo;
    }




}
