package com.dxt.boss.model;

import com.dxt.common.MyDateUtil;

import java.util.List;

public class BalanceInfo {

    private String realSpeBalance;
    private String expireDate;
    private String speBalance;
    private String realBalance;
    private String balance;
    private List<BalanceInformation> balanceInformation;
    private String currentDate;

    public String getRealSpeBalance() {
        return realSpeBalance;
    }

    public void setRealSpeBalance(String realSpeBalance) {
        this.realSpeBalance = realSpeBalance;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = MyDateUtil.getDateStringFromOtherFormatDateStr(expireDate,
                MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern());
    }

    public String getSpeBalance() {
        return speBalance;
    }

    public void setSpeBalance(String speBalance) {
        this.speBalance = speBalance;
    }

    public String getRealBalance() {
        return realBalance;
    }

    public void setRealBalance(String realBalance) {
        this.realBalance = realBalance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<BalanceInformation> getBalanceInformation() {
        return balanceInformation;
    }

    public void setBalanceInformation(List<BalanceInformation> balanceInformation) {
        this.balanceInformation = balanceInformation;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }


    static class BalanceInformation {

        private String balanceId;
        private String balanceUsed;
        private String balanceAvailable;
        private String balanceItemTypeDetail;
        private String allowTrans;
        private String unitTypeID;
        private String effDate;
        private String expDate;
        private String balanceReserved;
        private String balanceTypeId;
        private String spePayType;
        private String balanceAmount;

        public String getBalanceId() {
            return balanceId;
        }

        public void setBalanceId(String balanceId) {
            this.balanceId = balanceId;
        }

        public String getBalanceUsed() {
            return balanceUsed;
        }

        public void setBalanceUsed(String balanceUsed) {
            this.balanceUsed = balanceUsed;
        }

        public String getBalanceAvailable() {
            return balanceAvailable;
        }

        public void setBalanceAvailable(String balanceAvailable) {
            this.balanceAvailable = balanceAvailable;
        }

        public String getBalanceItemTypeDetail() {
            return balanceItemTypeDetail;
        }

        public void setBalanceItemTypeDetail(String balanceItemTypeDetail) {
            this.balanceItemTypeDetail = balanceItemTypeDetail;
        }

        public String getAllowTrans() {
            return allowTrans;
        }

        public void setAllowTrans(String allowTrans) {
            this.allowTrans = allowTrans;
        }

        public String getUnitTypeID() {
            return unitTypeID;
        }

        public void setUnitTypeID(String unitTypeID) {
            this.unitTypeID = unitTypeID;
        }

        public String getEffDate() {
            return effDate;
        }

        public void setEffDate(String effDate) {
            if ("-".equals(effDate)) {
                this.effDate = null;
            } else {
                this.effDate = MyDateUtil.getDateStringFromOtherFormatDateStr(effDate,
                        MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern());
            }
        }

        public String getExpDate() {
            return expDate;
        }

        public void setExpDate(String expDate) {
            this.expDate = MyDateUtil.getDateStringFromOtherFormatDateStr(expDate,
                    MyDateUtil.FormatPattern.YYYY_MM_DD.getFormatPattern());
        }

        public String getBalanceReserved() {
            return balanceReserved;
        }

        public void setBalanceReserved(String balanceReserved) {
            this.balanceReserved = balanceReserved;
        }

        public String getBalanceTypeId() {
            return balanceTypeId;
        }

        public void setBalanceTypeId(String balanceTypeId) {
            this.balanceTypeId = balanceTypeId;
        }

        public String getSpePayType() {
            return spePayType;
        }

        public void setSpePayType(String spePayType) {
            this.spePayType = spePayType;
        }

        public String getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(String balanceAmount) {
            this.balanceAmount = balanceAmount;
        }
    }

}
