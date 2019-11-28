package com.dxt.boss.model;

public class BaseCustInfo {

    private String custName;
    private String custCertCode;
    private String custCertAddress;
    private String custId;
    private String custCertType;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCertCode() {
        return custCertCode;
    }

    public void setCustCertCode(String custCertCode) {
        this.custCertCode = custCertCode;
    }

    public String getCustCertAddress() {
        return custCertAddress;
    }

    public void setCustCertAddress(String custCertAddress) {
        this.custCertAddress = custCertAddress;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustCertType() {
        return custCertType;
    }

    public void setCustCertType(String custCertType) {
        this.custCertType = custCertType;
    }

    @Override
    public String toString() {
        return "BaseCustInfo [" +
                "custName=" + custName +
                ", custCertType=" + custCertType +
                ", custCertCode=" + custCertCode +
                ", custCertAddress=" + custCertAddress +
                ", custId=" + custId + "]";
    }
}
