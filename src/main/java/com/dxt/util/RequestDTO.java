package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Root")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class RequestDTO {
	@XmlElement(name = "Head")
	protected RequestHeadDTO head = new RequestHeadDTO();

	public RequestHeadDTO getHead() {
		return head;
	}

	public void setHead(RequestHeadDTO head) {
		this.head = head;
	}

}
