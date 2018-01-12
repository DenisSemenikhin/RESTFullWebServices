package com.epam.WebServiceRF.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@XmlElement(name = "flyId")
	private String flyId;
	@XmlElement(name = "Human")
	private Human human;

	public Order() {
	}

	public String getFlyID() {
		return flyId;
	}

	public void setFlyID(String flyID) {
		this.flyId = flyID;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

}
