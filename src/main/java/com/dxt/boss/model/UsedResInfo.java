package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;

public class UsedResInfo {

    @JSONField(name = "in_voice")
    private String inVoice;
    @JSONField(name = "out_voice")
    private String outVoice;
    private String mms;
    private String msg;
    private String gprs;

    public String getInVoice() {
        return inVoice;
    }

    public void setInVoice(String inVoice) {
        this.inVoice = inVoice;
    }

    public String getOutVoice() {
        return outVoice;
    }

    public void setOutVoice(String outVoice) {
        this.outVoice = outVoice;
    }

    public String getMms() {
        return mms;
    }

    public void setMms(String mms) {
        this.mms = mms;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getGprs() {
        return gprs;
    }

    public void setGprs(String gprs) {
        this.gprs = gprs;
    }
}
