package com.dxt.model;

/**
 * @author Liuhh
 * @creat 2019-12-28 15:04
 */
public class TopResult {

    private String  code;
    private String result;
    private  String transactionId;

    public TopResult(String code, String result,String transactionId) {
        this.code = code;
        this.result = result;
        this.transactionId=transactionId;
    }

    public TopResult() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
