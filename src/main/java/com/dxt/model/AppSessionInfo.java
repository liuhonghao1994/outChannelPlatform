package com.dxt.model;

import com.dxt.common.MyObjectUtil;

import java.io.Serializable;
import java.util.Date;

public class AppSessionInfo implements Serializable {

    private static final long serialVersionUID = 5333891467462396430L;
    
    private String sessionId;
    private String phone;
    private String imei;
    private String pushId;
    private Date lastLoginTime = new Date();
    private String effective;
    private String network;
    private String userId;
    private String acctId;
    private String osType;
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    private void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    @Override
    public String toString() {
        return MyObjectUtil.show(this);
    }
}
