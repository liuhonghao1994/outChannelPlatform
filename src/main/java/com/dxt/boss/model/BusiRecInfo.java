package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.dxt.common.MyDateUtil;

public class BusiRecInfo {

    @JSONField(name = "Status")
    private String status;
    @JSONField(name = "OfferId")
    private String offerId;
    @JSONField(name = "BusiType")
    private String busiType;
    @JSONField(name = "ServiceNum")
    private String serviceNum;
    @JSONField(name = "ChannelName")
    private String channelName;
    @JSONField(name = "RecDate")
    private String recDate;
    @JSONField(name = "OpCode")
    private String opCode;
    @JSONField(name = "RecId")
    private String recId;
    @JSONField(name = "BrandId")
    private String brandId;
    @JSONField(name = "BusiCode")
    private String busiCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(String serviceNum) {
        this.serviceNum = serviceNum;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = MyDateUtil.getDateStringFromOtherFormatDateStr(recDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD_HH_MM_SS.getFormatPattern());
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }
}
