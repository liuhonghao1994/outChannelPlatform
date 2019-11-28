package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class Response {
	private String respType;
	private String respCode;
	private String respDesc;
	public String getRespType() {
		return respType;
	}
	public void setRespType(String respType) {
		this.respType = respType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	

}
