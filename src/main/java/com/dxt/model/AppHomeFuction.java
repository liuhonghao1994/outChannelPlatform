package com.dxt.model;

public class AppHomeFuction {

    private Integer funSort;
    private String funCode;
    private String funName;
    private String picUrl;

    public AppHomeFuction(AppHomeInfoBean appHomeInfoBean) {
        this.funSort = Integer.valueOf(appHomeInfoBean.getSort());
        this.funCode = appHomeInfoBean.getCode();
        this.funName = appHomeInfoBean.getName();
        this.picUrl = appHomeInfoBean.getImageUrl();
    }

    public Integer getFunSort() {
        return funSort;
    }

    public void setFunSort(Integer funSort) {
        this.funSort = funSort;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
