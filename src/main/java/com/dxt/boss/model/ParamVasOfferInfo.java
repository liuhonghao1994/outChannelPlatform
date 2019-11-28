package com.dxt.boss.model;

import java.util.List;

public class ParamVasOfferInfo {

    /**
     * OfferId : 111099700005
     * OperType : 0
     * AttrInstInfo : [{"AttrValue":"13915111754","AttrId":"151000305010"}]
     */

    private String OfferId;
    private String OperType;
    private List<AttrInstInfoBean> AttrInstInfo;

    public String getOfferId() {
        return OfferId;
    }

    public void setOfferId(String OfferId) {
        this.OfferId = OfferId;
    }

    public String getOperType() {
        return OperType;
    }

    public void setOperType(String OperType) {
        this.OperType = OperType;
    }

    public List<AttrInstInfoBean> getAttrInstInfo() {
        return AttrInstInfo;
    }

    public void setAttrInstInfo(List<AttrInstInfoBean> AttrInstInfo) {
        this.AttrInstInfo = AttrInstInfo;
    }

    public static class AttrInstInfoBean {
        /**
         * AttrValue : 13915111754
         * AttrId : 151000305010
         */

        private String AttrValue;
        private String AttrId;

        public String getAttrValue() {
            return AttrValue;
        }

        public void setAttrValue(String AttrValue) {
            this.AttrValue = AttrValue;
        }

        public String getAttrId() {
            return AttrId;
        }

        public void setAttrId(String AttrId) {
            this.AttrId = AttrId;
        }
    }
}
