package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "IdAuthRespBody")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class IdAuthRespBodyDTO extends BodyDTO {

	@XmlElement(name = "IdAuthResp")
	private IdAuthResp idAuthResp;

	public IdAuthResp getIdAuthResp() {
		return idAuthResp;
	}

	public void setIdAuthResp(IdAuthResp idAuthResp) {
		this.idAuthResp = idAuthResp;
	}

}
