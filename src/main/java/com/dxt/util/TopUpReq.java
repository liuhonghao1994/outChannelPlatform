package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RechargeBalanceReq")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class TopUpReq {

    private String reqSerial;
    private String destCode;
    private String feeAmount;

    public String getReqSerial() {
        return reqSerial;
    }

    public void setReqSerial(String reqSerial) {
        this.reqSerial = reqSerial;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }
}
