package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class TopUpRequestBody extends BodyDTO{
    @XmlElement(name = "RechargeBalanceReq")
    private TopUpReq topUpReq;

    public TopUpReq getTopUpReq() {
        return topUpReq;
    }

    public void setTopUpReq(TopUpReq topUpReq) {
        this.topUpReq = topUpReq;
    }
}
