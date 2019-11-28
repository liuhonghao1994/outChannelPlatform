package com.dxt.util;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;



@XmlRootElement(name = "ResponsetHead")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class ResponsetHeadDTO {

	private String actionCode;
	private String transactionId;
	@XmlJavaTypeAdapter(value = DateTimeAdapter.class)
	private Date respTime = new Date();
	@XmlElement(name = "Response")
	private Response response = new Response();

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public Date getRespTime() {
		return respTime;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
