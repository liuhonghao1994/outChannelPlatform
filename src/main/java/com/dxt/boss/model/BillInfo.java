package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;

public class BillInfo {
    /**
     * EndTime : 20180930235959
     * UnpayFee : 0.00
     * OtherPayFee : 0.00
     * BeginTime : 20180901000000
     * DiscountFee : 0.00
     * BillCycle : 201809
     * PayFee : 6.10
     */

    @JSONField(name = "EndTime")
    private String endTime;
    @JSONField(name = "UnpayFee")
    private String unpayFee;
    @JSONField(name = "OtherPayFee")
    private String otherPayFee;
    @JSONField(name = "BeginTime")
    private String beginTime;
    @JSONField(name = "DiscountFee")
    private String discountFee;
    @JSONField(name = "BillCycle")
    private String billCycle;
    @JSONField(name = "PayFee")
    private String payFee;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUnpayFee() {
        return unpayFee;
    }

    public void setUnpayFee(String unpayFee) {
        this.unpayFee = unpayFee;
    }

    public String getOtherPayFee() {
        return otherPayFee;
    }

    public void setOtherPayFee(String otherPayFee) {
        this.otherPayFee = otherPayFee;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }


}
