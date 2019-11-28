package com.dxt.boss.model;

import com.dxt.common.MyDateUtil;

import java.util.List;

public class RealTimeBillInfo {

    private String discountFee;
    private String otherPayFee;
    private String billCycle;
    private String beginTime;
    private String unpayFee;
    private String endTime;
    private String payFee;
    private List<BillingDetail> billingDetail;
    private String currentDate;

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }

    public String getOtherPayFee() {
        return otherPayFee;
    }

    public void setOtherPayFee(String otherPayFee) {
        this.otherPayFee = otherPayFee;
    }

    public String getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(String billCycle) {
        this.billCycle = billCycle;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = MyDateUtil.getDateStringFromOtherFormatDateStr(beginTime,
                MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern());
    }

    public String getUnpayFee() {
        return unpayFee;
    }

    public void setUnpayFee(String unpayFee) {
        this.unpayFee = unpayFee;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = MyDateUtil.getDateStringFromOtherFormatDateStr(endTime,
                MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern());
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public List<BillingDetail> getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(List<BillingDetail> billingDetail) {
        this.billingDetail = billingDetail;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    static class BillingDetail {

        private String itemNodeLevel;
        private String discountFee;
        private String itemName;
        private String billItemId;
        private String totalFee;
        private String itemType;
        private String originFee;
        private String totalCharge;
        private String parentItemName;
        private String parentItemId;
        private String adjustFee;

        public String getItemNodeLevel() {
            return itemNodeLevel;
        }

        public void setItemNodeLevel(String itemNodeLevel) {
            this.itemNodeLevel = itemNodeLevel;
        }

        public String getDiscountFee() {
            return discountFee;
        }

        public void setDiscountFee(String discountFee) {
            this.discountFee = discountFee;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getBillItemId() {
            return billItemId;
        }

        public void setBillItemId(String billItemId) {
            this.billItemId = billItemId;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getOriginFee() {
            return originFee;
        }

        public void setOriginFee(String originFee) {
            this.originFee = originFee;
        }

        public String getTotalCharge() {
            return totalCharge;
        }

        public void setTotalCharge(String totalCharge) {
            this.totalCharge = totalCharge;
        }

        public String getParentItemName() {
            return parentItemName;
        }

        public void setParentItemName(String parentItemName) {
            this.parentItemName = parentItemName;
        }

        public String getParentItemId() {
            return parentItemId;
        }

        public void setParentItemId(String parentItemId) {
            this.parentItemId = parentItemId;
        }

        public String getAdjustFee() {
            return adjustFee;
        }

        public void setAdjustFee(String adjustFee) {
            this.adjustFee = adjustFee;
        }
    }

}
