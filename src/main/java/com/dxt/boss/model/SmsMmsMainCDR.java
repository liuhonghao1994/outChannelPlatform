package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class SmsMmsMainCDR {

    private String colorNumberSum;
    private String colorChargeSum;
    private String commonChargeSum;
    private String commonNumberSum;
    @JSONField(name = "SDrSmsMmsDetailListVO")
    private List<SdrSmsMmsDetailList> sdrSmsMmsDetailList;

    public String getColorNumberSum() {
        return colorNumberSum;
    }

    public void setColorNumberSum(String colorNumberSum) {
        this.colorNumberSum = colorNumberSum;
    }

    public String getColorChargeSum() {
        return colorChargeSum;
    }

    public void setColorChargeSum(String colorChargeSum) {
        this.colorChargeSum = colorChargeSum;
    }

    public String getCommonChargeSum() {
        return commonChargeSum;
    }

    public void setCommonChargeSum(String commonChargeSum) {
        this.commonChargeSum = commonChargeSum;
    }

    public String getCommonNumberSum() {
        return commonNumberSum;
    }

    public void setCommonNumberSum(String commonNumberSum) {
        this.commonNumberSum = commonNumberSum;
    }

    public List<SdrSmsMmsDetailList> getSdrSmsMmsDetailList() {
        return sdrSmsMmsDetailList;
    }

    public void setSdrSmsMmsDetailList(List<SdrSmsMmsDetailList> sdrSmsMmsDetailList) {
        this.sdrSmsMmsDetailList = sdrSmsMmsDetailList;
    }

    static class SdrSmsMmsDetailList {
        @JSONField(name = "valid_rate_prod_id")
        private String validRateProdId;
        @JSONField(name = "info_type")
        private String infoType;
        @JSONField(name = "operator_code")
        private Object operatorCode;
        private String charge;
        private String prodName;
        @JSONField(name = "call_type")
        private String callType;
        private String hplmnName;
        private String callName;
        @JSONField(name = "start_time")
        private String startTime;
        @JSONField(name = "opp_number")
        private String oppNumber;
        private String hplmn;

        public String getValidRateProdId() {
            return validRateProdId;
        }

        public void setValidRateProdId(String validRateProdId) {
            this.validRateProdId = validRateProdId;
        }

        public String getInfoType() {
            return infoType;
        }

        public void setInfoType(String infoType) {
            this.infoType = infoType;
        }

        public Object getOperatorCode() {
            return operatorCode;
        }

        public void setOperatorCode(Object operatorCode) {
            this.operatorCode = operatorCode;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public String getCallType() {
            return callType;
        }

        public void setCallType(String callType) {
            this.callType = callType;
        }

        public String getHplmnName() {
            return hplmnName;
        }

        public void setHplmnName(String hplmnName) {
            this.hplmnName = hplmnName;
        }

        public String getCallName() {
            return callName;
        }

        public void setCallName(String callName) {
            this.callName = callName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getOppNumber() {
            return oppNumber;
        }

        public void setOppNumber(String oppNumber) {
            this.oppNumber = oppNumber;
        }

        public String getHplmn() {
            return hplmn;
        }

        public void setHplmn(String hplmn) {
            this.hplmn = hplmn;
        }
    }

}
