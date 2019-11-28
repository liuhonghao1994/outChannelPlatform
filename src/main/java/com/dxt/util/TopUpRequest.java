package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class TopUpRequest {
    @XmlElement(name = "Head")
    private RequestHeadDTO head = new RequestHeadDTO();

    @XmlElement(name = "Body")
    private TopUpRequestBody body = null;

    public RequestHeadDTO getHead() {
        return head;
    }

    public void setHead(RequestHeadDTO head) {
        this.head = head;
    }

    public TopUpRequestBody getBody() {
        return body;
    }

    public void setBody(TopUpRequestBody body) {
        this.body = body;
    }
}
