package com.dxt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.dxt.common.AppConstant;
import com.dxt.common.MyBusiException;
import com.dxt.message.ReponseMessage;
import com.dxt.message.AppRequestMessage;
import com.dxt.model.AppSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class IBaseBusiService {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AppRequestMessage msg;

    @Autowired
    private SessionManager sessionManager;

    abstract public ReponseMessage execute(String reqInfo, String source);

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public AppRequestMessage getMsg() {
        return msg;
    }

    public void setMsg(AppRequestMessage msg) {
        this.msg = msg;
    }

    protected String getAppRequestOsType() {
        return this.msg.getAppType();
    }

    protected void checkParams(JSONObject jsonObject, String... args) throws MyBusiException {
        for (String param : args) {
            if (null == jsonObject.getString(param) || "".equals(jsonObject.getString(param))) {
                throw new MyBusiException("参数" + param + "不可为空！");
            }
        }
    }

    protected String getPhoneFromSessionOrParam(JSONObject jsonObject, String source) throws MyBusiException {
        String phone = null;
        // 此处默认app来校验的都是强制要求存在session的，如果非强制，子类自己进行判断
        if (AppConstant.REQUEST_SOURCE.APP.getValue().equals(source)) {
            AppSessionInfo appSessionInfo;
            appSessionInfo = sessionManager.getSessionInfoFromHttpServletRequest(getRequest());
            phone = appSessionInfo.getPhone();
            if (null == phone) {
                phone = jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
            }
        }else {
            phone = jsonObject.getString(AppConstant.REQUEST_REPONSE_PARAM.PARAM_IN_PHONE);
        }



        if (null == phone) {
            throw new MyBusiException("获取参数错误！");
        }
        return phone;
    }



}
