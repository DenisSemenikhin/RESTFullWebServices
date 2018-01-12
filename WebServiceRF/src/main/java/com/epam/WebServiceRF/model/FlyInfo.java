package com.epam.WebServiceRF.model;

import java.util.Date;

public class FlyInfo {

	private String flyId;
	private String departureCity;
	private String arrivalCity;
	private Date departureDate;
	private Date arrivalDate;
	private Money cost;

	public FlyInfo() {
	}

	FlyInfo(String flyId, String departureCity, String arrivalCity, Date departureDate, Date arrivalDate, Money cost) {
		this.flyId = flyId;
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.cost = cost;
	}

	public String getFlyId() {
		return flyId;
	}

	public void setFlyId(String flyId) {
		this.flyId = flyId;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Money getCost() {
		return cost;
	}

	public void setCost(Money cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return getFlyId() + getDepartureCity() + getArrivalCity() + getDepartureDate() + getArrivalDate() + getCost();
	}

}
