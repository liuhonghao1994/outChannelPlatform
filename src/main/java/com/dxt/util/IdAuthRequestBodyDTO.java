package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "IdAuthRequestBody")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class IdAuthRequestBodyDTO extends BodyDTO {

	@XmlElement(name = "IdAuthReq")
	private IdAuthReq idAuthReq;

	public IdAuthReq getIdAuthReq() {
		return idAuthReq;
	}

	public void setIdAuthReq(IdAuthReq idAuthReq) {
		this.idAuthReq = idAuthReq;
	}

}
