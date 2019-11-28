package com.dxt.boss.model;

import java.io.Serializable;

public class PhoneModle  implements Serializable{
    private String bill_id;
    private String cust_cert_code;
    private int status;


    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getCust_cert_code() {
        return cust_cert_code;
    }

    public void setCust_cert_code(String cust_cert_code) {
        this.cust_cert_code = cust_cert_code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
