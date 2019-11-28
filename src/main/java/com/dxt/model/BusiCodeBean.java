package com.dxt.model;

import java.io.Serializable;

public class BusiCodeBean implements Serializable {

    private static final long serialVersionUID = 5333891467462396430L;
    
    private String busiCode;
    private String beanName;
    private String description;
    private String requireLogin;

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequireLogin() {
        return requireLogin;
    }

    public void setRequireLogin(String requireLogin) {
        this.requireLogin = requireLogin;
    }

    @Override
    public String toString() {
        return "BusiCodeBean [" +
                "busiCode=" + busiCode +
                ", beanName=" + beanName +
                ", description=" + description +
                ", requireLogin=" + requireLogin + "]";
    }
}
