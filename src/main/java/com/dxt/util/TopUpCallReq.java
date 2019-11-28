package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RechargeCallbackReq")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class TopUpCallReq {


    private String transactionId ;
    private String platTransactionId ;
    private String destCode ;
    private String feeAmount ;
    private String status ;
    private String returnCode ;
    private String errorMsg  ;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPlatTransactionId() {
        return platTransactionId;
    }

    public void setPlatTransactionId(String platTransactionId) {
        this.platTransactionId = platTransactionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
