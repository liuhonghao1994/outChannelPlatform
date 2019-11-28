package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class CallCDR {

    private String manYouSumCharge;
    private String shiHuaCharge;
    private String guoJiLongCharge;
    private String guojiRoamCharge;
    private String changTuSumcharge;
    private String guoNeiLongCharge;
    private String sumCharge;
    private String guoNeiRoamCharge;
    private String localSumcharge;
    @JSONField(name = "SDrCallDetailSet")
    private List<SdrCallDetail> sdrCallDetailList;

    public String getManYouSumCharge() {
        return manYouSumCharge;
    }

    public void setManYouSumCharge(String manYouSumCharge) {
        this.manYouSumCharge = manYouSumCharge;
    }

    public String getShiHuaCharge() {
        return shiHuaCharge;
    }

    public void setShiHuaCharge(String shiHuaCharge) {
        this.shiHuaCharge = shiHuaCharge;
    }

    public String getGuoJiLongCharge() {
        return guoJiLongCharge;
    }

    public void setGuoJiLongCharge(String guoJiLongCharge) {
        this.guoJiLongCharge = guoJiLongCharge;
    }

    public String getGuojiRoamCharge() {
        return guojiRoamCharge;
    }

    public void setGuojiRoamCharge(String guojiRoamCharge) {
        this.guojiRoamCharge = guojiRoamCharge;
    }

    public String getChangTuSumcharge() {
        return changTuSumcharge;
    }

    public void setChangTuSumcharge(String changTuSumcharge) {
        this.changTuSumcharge = changTuSumcharge;
    }

    public String getGuoNeiLongCharge() {
        return guoNeiLongCharge;
    }

    public void setGuoNeiLongCharge(String guoNeiLongCharge) {
        this.guoNeiLongCharge = guoNeiLongCharge;
    }

    public String getSumCharge() {
        return sumCharge;
    }

    public void setSumCharge(String sumCharge) {
        this.sumCharge = sumCharge;
    }

    public String getGuoNeiRoamCharge() {
        return guoNeiRoamCharge;
    }

    public void setGuoNeiRoamCharge(String guoNeiRoamCharge) {
        this.guoNeiRoamCharge = guoNeiRoamCharge;
    }

    public String getLocalSumcharge() {
        return localSumcharge;
    }

    public void setLocalSumcharge(String localSumcharge) {
        this.localSumcharge = localSumcharge;
    }

    public List<SdrCallDetail> getSdrCallDetailList() {
        return sdrCallDetailList;
    }

    public void setSdrCallDetailList(List<SdrCallDetail> sdrCallDetailList) {
        this.sdrCallDetailList = sdrCallDetailList;
    }

}
