package com.dxt.boss.model;

import java.io.Serializable;

public class UseBeanOfQueryPhone implements Serializable {

    String bill_id;
    String icc_id;
    String status;

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getIcc_id() {
        return icc_id;
    }

    public void setIcc_id(String icc_id) {
        this.icc_id = icc_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
