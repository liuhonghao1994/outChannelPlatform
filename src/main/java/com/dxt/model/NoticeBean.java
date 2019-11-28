package com.dxt.model;

import java.io.Serializable;

public class NoticeBean implements Serializable {

    private static final long serialVersionUID = 5333891467462396430L;

    private Integer id;
    private String versionCode;
    private String appType;
    private String title;
    private String summary;
    private String url;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "id=" + id +
                ", versionCode=" + versionCode +
                ", appType=" + appType +
                ", title=" + title +
                ", summary=" + summary +
                ", url=" + url +
                ", remark=" + remark +
                "}";
    }
}
