package com.dxt.boss.model;
//密钥
public class SecretKeyModel {
    private  String  openId;
    private String openIdSecret;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenIdSecret() {
        return openIdSecret;
    }

    public void setOpenIdSecret(String openIdSecret) {
        this.openIdSecret = openIdSecret;
    }
}
