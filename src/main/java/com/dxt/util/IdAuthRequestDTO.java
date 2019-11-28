package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "Root")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class IdAuthRequestDTO {
	@XmlElement(name = "Head")
	private RequestHeadDTO head = new RequestHeadDTO();
	@XmlElement(name = "Body")
	private IdAuthRequestBodyDTO body = null;

	public RequestHeadDTO getHead() {
		return head;
	}

	public void setHead(RequestHeadDTO head) {
		this.head = head;
	}

	public IdAuthRequestBodyDTO getBody() {
		return body;
	}

	public void setBody(IdAuthRequestBodyDTO body) {
		this.body = body;
	}

}
