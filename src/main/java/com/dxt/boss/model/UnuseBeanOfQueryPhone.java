package com.dxt.boss.model;

import java.io.Serializable;

public class UnuseBeanOfQueryPhone  implements Serializable {
    private  String bill_id;
    private String icc_id;

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    private String status;

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
