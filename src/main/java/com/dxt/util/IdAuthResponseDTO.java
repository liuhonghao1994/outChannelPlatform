package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Root")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class IdAuthResponseDTO extends ResponseDTO {

	@XmlElement(name = "Body")
	private IdAuthRespBodyDTO body = null;

	public BodyDTO getBody() {
		return body;
	}

	public void setBody(IdAuthRespBodyDTO body) {
		this.body = body;
	}

}
