package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dxt.common.MyDateUtil;

public class PayAndWriteoffRec {

    private String busiType;
    @JSONField(name = "PayChannel")
    private String payChannel;
    @JSONField(name = "LateFee")
    private String lateFee;
    @JSONField(name = "WriteoffFee")
    private String writeoffFee;
    @JSONField(name = "ReceivedFee")
    private String receivedFee;
    @JSONField(name = "PayTime")
    private String payTime;
    private String afterBalance;
    @JSONField(name = "ReceivableFee")
    private String receivableFee;
    private String beforeBalance;
    private String busiName;

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getLateFee() {
        return lateFee;
    }

    public void setLateFee(String lateFee) {
        this.lateFee = lateFee;
    }

    public String getWriteoffFee() {
        return writeoffFee;
    }

    public void setWriteoffFee(String writeoffFee) {
        this.writeoffFee = writeoffFee;
    }

    public String getReceivedFee() {
        return receivedFee;
    }

    public void setReceivedFee(String receivedFee) {
        this.receivedFee = receivedFee;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = MyDateUtil.getDateStringFromOtherFormatDateStr(payTime,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(String afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getReceivableFee() {
        return receivableFee;
    }

    public void setReceivableFee(String receivableFee) {
        this.receivableFee = receivableFee;
    }

    public String getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(String beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }
}
