package com.dxt.message;

public class AppRequestMessage {


    /**
     * 客户端类型：android，ios
     */
    private String appType;
    /**
     * 版本号
     */
    private String version;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * uuid
     */
    private String uuId;
    /**
     * 加密密文
     */
    private String sign;
    /**
     * 业务编码
     */
    private String busiCode;
    /**
     * 业务请求内容
     */
    private String reqInfo;
    /**
     * 客户端随机数编码（用于加密）
     */
    private String clientCode;

    /**
     *   传真实的登录用户渠道编码
     */

    private String channelId;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 传真实的登录用户操作员编码
     * @return
     */


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 请求密钥
     *
     */

    private  String secretKey;
    private  String  operatorId;
    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(String reqInfo) {
        this.reqInfo = reqInfo;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public AppRequestMessage() {
        super();
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppType() {
        return appType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
    
    public boolean isAllInfoValid() {
        if (null != this.appType && !"".equals(this.appType) &&
            null != this.version && !"".equals(this.version) &&
            null != this.timestamp && !"".equals(this.timestamp) &&
            null != this.uuId && !"".equals(this.uuId) &&
            null != this.sign && !"".equals(this.sign) &&
            null != this.busiCode && !"".equals(this.busiCode) &&
            null != this.clientCode && !"".equals(this.clientCode)) {
            return true;
        }
        return false;
    }


    public  boolean  isOpenParmsvalide(){
        if (null != this.appType && !"".equals(this.appType) &&
                null != this.version &&
                null != this.timestamp && !"".equals(this.timestamp) &&
                null != this.uuId &&
                null != this.sign && !"".equals(this.sign) &&
                null != this.busiCode && !"".equals(this.busiCode) &&
                null != this.clientCode && !"".equals(this.clientCode) &&
                null != this.secretKey && !"".equals(this.secretKey) &&
                null != this.channelId && !"".equals(this.channelId) &&
                null != this.operatorId && !"".equals(this.operatorId)) {
            return  true;
        }
        return  false;
    }

}
