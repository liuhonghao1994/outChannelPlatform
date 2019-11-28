package com.dxt.model;

import com.dxt.common.AppConstant;
import com.dxt.common.MyObjectUtil;
import com.sun.tools.corba.se.idl.constExpr.ShiftRight;

import java.io.Serializable;
import java.util.Date;

public class AppUserBasicInfoBean implements Serializable {
    
    private String phone;
    private String regionId = "";
    private String regionName = "";
    private String state = "";
    private String stateDesc = "";
    private String osStatus = "";
    private String osStatusDesc = "";
    private String accStatus = "";
    private String accStatusDesc = "";
    private String custCertType = "";
    private String custCertCode = "";
    private String custName = "";
    private String offerId = "";
    private String offerName = "";
    private String avatarUrl = "";
    private String acctId = "";
    private String userId = "";
    private String network = "";
    private String iccid="";


    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getCustCertType() {
        return custCertType;
    }

    public void setCustCertType(String custCertType) {
        this.custCertType = custCertType;
    }

    public String getCustCertCode() {
        return custCertCode;
    }

    public void setCustCertCode(String custCertCode) {
        this.custCertCode = custCertCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        if (null != offerId && !"".equals(offerId)) {
            this.offerId = String.valueOf(Long.parseLong(offerId) +
                    AppConstant.SYS_CONSTANT.PROD_INTERFERENCE_CODE);
        } else {
            this.offerId = offerId;
        }
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getOsStatusDesc() {
        return osStatusDesc;
    }

    public void setOsStatusDesc(String osStatusDesc) {
        this.osStatusDesc = osStatusDesc;
    }

    public String getAccStatusDesc() {
        return accStatusDesc;
    }

    public void setAccStatusDesc(String accStatusDesc) {
        this.accStatusDesc = accStatusDesc;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return MyObjectUtil.show(this);
    }
    
}
