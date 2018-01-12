package com.epam.WebClientRF.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Human")
@XmlAccessorType(XmlAccessType.FIELD)
public class Human {

	@XmlElement(name = "fName")
	private String fName;
	@XmlElement(name = "lName")
	private String lName;
	@XmlElement(name = "patronomyc")
	private String patronomyc;
	@XmlElement(name = "birthDate")
	private Date birthDate;

	public Human() {
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getPatronomyc() {
		return patronomyc;
	}

	public void setPatronomyc(String patronomyc) {
		this.patronomyc = patronomyc;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
