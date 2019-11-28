package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class TopUpCallBackRequestBody extends BodyDTO{
    @XmlElement(name = "RechargeCallbackReq")
    private TopUpCallReq topUpCallReq;

    public TopUpCallReq getTopUpCallReq() {
        return topUpCallReq;
    }

    public void setTopUpCallReq(TopUpCallReq topUpCallReq) {
        this.topUpCallReq = topUpCallReq;
    }
}
