package com.dxt.model;

import java.io.Serializable;

public class UploadInfoBean implements Serializable {

    private static final long serialVersionUID = 5333891467462396430L;

    private String versionCode;
    private String appType;
    private String infoType;
    private String content;
    private String remark;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UploadInfoBean{" +
                "versionCode=" + versionCode +
                ", appType=" + appType +
                ", infoType=" + infoType +
                ", content=" + content +
                ", remark=" + remark +
                "}";
    }
}
