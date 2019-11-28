package com.dxt.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class AppVersionBean implements Serializable {
    
    private static final long serialVersionUID = 6796432562406023839L;
    
    private Integer id;
    private String versionCode;
    private String versionName;
    private String updateType;
    private String url;
    private String versionDesc;
    private String type;
    private Timestamp uploadTime;
    private String updateSummary;
    
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
    public String getVersionName() {
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    public String getUpdateType() {
        return updateType;
    }
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getVersionDesc() {
        return versionDesc;
    }
    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Timestamp getUploadTime() {
        return uploadTime;
    }
    private void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUpdateSummary() {
        return updateSummary;
    }

    public void setUpdateSummary(String updateSummary) {
        this.updateSummary = updateSummary;
    }
}
