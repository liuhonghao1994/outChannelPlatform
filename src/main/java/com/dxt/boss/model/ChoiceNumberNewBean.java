package com.dxt.boss.model;

import java.io.Serializable;

public class ChoiceNumberNewBean implements Serializable {

    private String provice;
    private String pro_code;
    private String city;
    private String hrl;
    private String res_id;
    private String region_id;


    private String manage_status;
    private String reserve_fee;
    private String deposit_month;
    private String deposit_amount;
    private String res_level;
    private String pattern_def_name;
    private String net;
    private int rsrv_num1;

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHrl() {
        return hrl;
    }

    public void setHrl(String hrl) {
        this.hrl = hrl;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getManage_status() {
        return manage_status;
    }

    public void setManage_status(String manage_status) {
        this.manage_status = manage_status;
    }

    public String getReserve_fee() {
        return reserve_fee;
    }

    public void setReserve_fee(String reserve_fee) {
        this.reserve_fee = reserve_fee;
    }

    public String getDeposit_month() {
        return deposit_month;
    }

    public void setDeposit_month(String deposit_month) {
        this.deposit_month = deposit_month;
    }

    public String getDeposit_amount() {
        return deposit_amount;
    }

    public void setDeposit_amount(String deposit_amount) {
        this.deposit_amount = deposit_amount;
    }

    public String getRes_level() {
        return res_level;
    }

    public void setRes_level(String res_level) {
        this.res_level = res_level;
    }

    public String getPattern_def_name() {
        return pattern_def_name;
    }

    public void setPattern_def_name(String pattern_def_name) {
        this.pattern_def_name = pattern_def_name;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public int getRsrv_num1() {
        return rsrv_num1;
    }

    public void setRsrv_num1(int rsrv_num1) {
        this.rsrv_num1 = rsrv_num1;
    }
}
