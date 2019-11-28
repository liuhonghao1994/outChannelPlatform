package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;

public class SdrCallDetail {

    @JSONField(name = "charge_disc")
    private String chargeDisc;
    private String charge;
    @JSONField(name = "valid_rate_prod_id")
    private String validRateProdId;
    private String hplmn;
    @JSONField(name = "opp_number")
    private String oppNumber;
    private String duration;
    @JSONField(name = "start_time")
    private String startTime;
    private String hplmnName;
    private String tollName;
    private String prodName;
    private String callName;
    @JSONField(name = "toll_type")
    private String tollType;
    @JSONField(name = "call_type")
    private String callType;
    private String shortNum;

    public String getChargeDisc() {
        return chargeDisc;
    }

    public void setChargeDisc(String chargeDisc) {
        this.chargeDisc = chargeDisc;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getValidRateProdId() {
        return validRateProdId;
    }

    public void setValidRateProdId(String validRateProdId) {
        this.validRateProdId = validRateProdId;
    }

    public String getHplmn() {
        return hplmn;
    }

    public void setHplmn(String hplmn) {
        this.hplmn = hplmn;
    }

    public String getOppNumber() {
        return oppNumber;
    }

    public void setOppNumber(String oppNumber) {
        this.oppNumber = oppNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getHplmnName() {
        return hplmnName;
    }

    public void setHplmnName(String hplmnName) {
        this.hplmnName = hplmnName;
    }

    public String getTollName() {
        return tollName;
    }

    public void setTollName(String tollName) {
        this.tollName = tollName;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getTollType() {
        return tollType;
    }

    public void setTollType(String tollType) {
        this.tollType = tollType;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getShortNum() {
        return shortNum;
    }

    public void setShortNum(String shortNum) {
        this.shortNum = shortNum;
    }

}
