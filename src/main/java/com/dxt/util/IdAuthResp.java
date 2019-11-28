package com.dxt.util;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IdAuthResp")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class IdAuthResp {

	private String name;
	private String idCode;
	private String mobile;
	private String birthday;
	private String age;
	private String gender;
	private String astro;
	private String animals;
	private String idTagNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAstro() {
		return astro;
	}

	public void setAstro(String astro) {
		this.astro = astro;
	}

	public String getAnimals() {
		return animals;
	}

	public void setAnimals(String animals) {
		this.animals = animals;
	}

	public String getIdTagNo() {
		return idTagNo;
	}

	public void setIdTagNo(String idTagNo) {
		this.idTagNo = idTagNo;
	}

}
