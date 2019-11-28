package com.dxt.boss.model;

import java.util.Date;

//用户信息查询表
public class UserInfoModle {

    private String bill_id;
    private String state;
    private String os_status;
    private String offer_id;
    private String order_name;
    private String cust_name;
    private String cust_cert_type;
    private String cust_cert_code;
    private String cust_cert_address;
    private String expire_date;

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String create_date;
    private String effective_date;
    private String user_id;

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOs_status() {
        return os_status;
    }

    public void setOs_status(String os_status) {
        this.os_status = os_status;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_cert_type() {
        return cust_cert_type;
    }

    public void setCust_cert_type(String cust_cert_type) {
        this.cust_cert_type = cust_cert_type;
    }

    public String getCust_cert_code() {
        return cust_cert_code;
    }

    public void setCust_cert_code(String cust_cert_code) {
        this.cust_cert_code = cust_cert_code;
    }

    public String getCust_cert_address() {
        return cust_cert_address;
    }

    public void setCust_cert_address(String cust_cert_address) {
        this.cust_cert_address = cust_cert_address;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getEffective_date() {
        return effective_date;
    }

    public void setEffective_date(String effective_date) {
        this.effective_date = effective_date;
    }
}
