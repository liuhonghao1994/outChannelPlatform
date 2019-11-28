package com.dxt.boss.model;

import java.util.ArrayList;
import java.util.List;

public class UserBillDetail {

    List<BillingDetail> billingDetail = new ArrayList<>();

    BillInfo billInfo = new BillInfo();

    public List<BillingDetail> getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(List<BillingDetail> billingDetail) {
        this.billingDetail = billingDetail;
    }

    public BillInfo getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(BillInfo billInfo) {
        this.billInfo = billInfo;
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
