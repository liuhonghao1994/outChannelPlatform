package com.dxt.model;

import java.io.Serializable;

public class AuditStatusBean implements Serializable {



    private String bill_id;
    private String audit_date;
    private int status_code;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    private String status;
    private String audit_msg;

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(String audit_date) {
        this.audit_date = audit_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAudit_msg() {
        return audit_msg;
    }

    public void setAudit_msg(String audit_msg) {
        this.audit_msg = audit_msg;
    }
}
