package com.dxt.boss.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class NetCDR {

    @JSONField(name = "WLANChargeSum")
    private String wlanChargeSum;
    @JSONField(name = "WLANFlowSum")
    private String wlanFlowSum;
    @JSONField(name = "WLANNumberSum")
    private String wlanNumberSum;
    @JSONField(name = "FlowSum")
    private String flowSum;
    @JSONField(name = "CMNETNumber")
    private String cmNETNumber;
    @JSONField(name = "CMWAPNumber")
    private String cmWAPNumber;
    @JSONField(name = "WLANTimeSum")
    private String wlanTimeSum;
    @JSONField(name = "CMNETFlow")
    private String cmNETFlow;
    @JSONField(name = "CMWAPFlow")
    private String cmWAPFlow;
    @JSONField(name = "GPRSNumberSum")
    private String gprsNumberSum;
    @JSONField(name = "SDrNetDetailListVO")
    private List<SdrNetDetail> sdrNetDetailList;

    public String getWlanChargeSum() {
        return wlanChargeSum;
    }

    public void setWlanChargeSum(String wlanChargeSum) {
        this.wlanChargeSum = wlanChargeSum;
    }

    public String getWlanFlowSum() {
        return wlanFlowSum;
    }

    public void setWlanFlowSum(String wlanFlowSum) {
        this.wlanFlowSum = wlanFlowSum;
    }

    public String getWlanNumberSum() {
        return wlanNumberSum;
    }

    public void setWlanNumberSum(String wlanNumberSum) {
        this.wlanNumberSum = wlanNumberSum;
    }

    public String getFlowSum() {
        return flowSum;
    }

    public void setFlowSum(String flowSum) {
        this.flowSum = flowSum;
    }

    public String getCmNETNumber() {
        return cmNETNumber;
    }

    public void setCmNETNumber(String cmNETNumber) {
        this.cmNETNumber = cmNETNumber;
    }

    public String getCmWAPNumber() {
        return cmWAPNumber;
    }

    public void setCmWAPNumber(String cmWAPNumber) {
        this.cmWAPNumber = cmWAPNumber;
    }

    public String getWlanTimeSum() {
        return wlanTimeSum;
    }

    public void setWlanTimeSum(String wlanTimeSum) {
        this.wlanTimeSum = wlanTimeSum;
    }

    public String getCmNETFlow() {
        return cmNETFlow;
    }

    public void setCmNETFlow(String cmNETFlow) {
        this.cmNETFlow = cmNETFlow;
    }

    public String getCmWAPFlow() {
        return cmWAPFlow;
    }

    public void setCmWAPFlow(String cmWAPFlow) {
        this.cmWAPFlow = cmWAPFlow;
    }

    public String getGprsNumberSum() {
        return gprsNumberSum;
    }

    public void setGprsNumberSum(String gprsNumberSum) {
        this.gprsNumberSum = gprsNumberSum;
    }

    public List<SdrNetDetail> getSdrNetDetailList() {
        return sdrNetDetailList;
    }

    public void setSdrNetDetailList(List<SdrNetDetail> sdrNetDetailList) {
        this.sdrNetDetailList = sdrNetDetailList;
    }

    static class SdrNetDetail {
        @JSONField(name = "valid_rate_prod_id")
        private String validRateProdId;
        private String duration;
        private String charge;
        private String prodName;
        @JSONField(name = "data_flow_total")
        private String dataFlowTotal;
        private String hplmnName;
        @JSONField(name = "charge_disc")
        private String chargeDisc;
        private String inputType;
        @JSONField(name = "start_time")
        private String startTime;
        @JSONField(name = "dr_type")
        private String drType;
        private String hplmn;

        public String getValidRateProdId() {
            return validRateProdId;
        }

        public void setValidRateProdId(String validRateProdId) {
            this.validRateProdId = validRateProdId;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
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

        public String getDataFlowTotal() {
            return dataFlowTotal;
        }

        public void setDataFlowTotal(String dataFlowTotal) {
            this.dataFlowTotal = dataFlowTotal;
        }

        public String getHplmnName() {
            return hplmnName;
        }

        public void setHplmnName(String hplmnName) {
            this.hplmnName = hplmnName;
        }

        public String getChargeDisc() {
            return chargeDisc;
        }

        public void setChargeDisc(String chargeDisc) {
            this.chargeDisc = chargeDisc;
        }

        public String getInputType() {
            return inputType;
        }

        public void setInputType(String inputType) {
            this.inputType = inputType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getDrType() {
            return drType;
        }

        public void setDrType(String drType) {
            this.drType = drType;
        }

        public String getHplmn() {
            return hplmn;
        }

        public void setHplmn(String hplmn) {
            this.hplmn = hplmn;
        }
    }

}
