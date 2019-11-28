package com.dxt.boss.model;

public class BaseUserInfo {

    private String userId;
    private String billId;
    private String state;
    private String osStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOsStatus() {
        return osStatus;
    }

    public void setOsStatus(String osStatus) {
        this.osStatus = osStatus;
    }

    @Override
    public String toString() {
        return "BaseUserInfo [" +
                "userId=" + userId +
                ", billId=" + billId +
                ", state=" + state +
                ", osStatus=" + osStatus + "]";
    }
}
