package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class TopUpCallBackRequest {
    @XmlElement(name = "Head")
    private RequestHeadDTO head = new RequestHeadDTO();

    @XmlElement(name = "Body")
    private TopUpCallBackRequestBody body = null;

    public RequestHeadDTO getHead() {
        return head;
    }

    public void setHead(RequestHeadDTO head) {
        this.head = head;
    }

    public TopUpCallBackRequestBody getBody() {
        return body;
    }

    public void setBody(TopUpCallBackRequestBody body) {
        this.body = body;
    }
}
