package com.dxt.message;

import com.alibaba.fastjson.JSONObject;
import com.dxt.common.MyObjectUtil;

/**
 * 错误信息处理
 */
public class ReponseMessage {

    private String code;
    private String message;
    private Object content;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ReponseMessage() {
    }

    public ReponseMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReponseMessage(String code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public void setMsg(String code, String message) {
        this.code = code;
        this.message = message;
        this.content = new JSONObject();
    }

    public void setMsg(String code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    @Override
    public String toString() {
        return MyObjectUtil.show(this);
    }
}
