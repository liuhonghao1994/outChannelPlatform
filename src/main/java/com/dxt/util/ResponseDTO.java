package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class ResponseDTO {

	@XmlElement(name = "Head")
	protected ResponsetHeadDTO head = new ResponsetHeadDTO();

	public ResponsetHeadDTO getHead() {
		return head;
	}

	public void setHead(ResponsetHeadDTO head) {
		this.head = head;
	}

}
