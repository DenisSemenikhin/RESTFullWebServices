package com.epam.WebServiceRF.model;

import java.io.IOException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.epam.WebServiceRF.dao.TicketManager;

@XmlRootElement(name = "FlyTicket")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlyTicket {

	@XmlElement(name = "Human")
	private Human human;
	@XmlElement(name = "ticketNumber")
	private int ticketNumber;
	@XmlElement(name = "departureCity")
	private String departureCity;
	@XmlElement(name = "arrivalCity")
	private String arrivalCity;
	@XmlElement(name = "departureDate")
	private Date departureDate;
	@XmlElement(name = "arrivalDate")
	private Date arrivalDate;
	@XmlElement(name = "ticketStatus")
	private TicketStatus ticketStatus;
	@XmlElement(name = "ticketCost")
	private Money ticketCost;

	public FlyTicket() {
	}

	FlyTicket(FlyInfo flyInfo, Human human) throws IOException {
		this.ticketNumber = (int) TicketManager.getUniqueTicketNumber();
		this.departureCity = flyInfo.getDepartureCity();
		this.arrivalCity = flyInfo.getArrivalCity();
		this.departureDate = flyInfo.getDepartureDate();
		this.arrivalDate = flyInfo.getArrivalDate();
		this.ticketStatus = TicketStatus.booked;
		this.ticketCost = flyInfo.getCost();
		this.human = human;
	}

	public FlyTicket(long ticketNumber, String departureCity, String arrivalCity, Date departureDate, Date arrivalDate, TicketStatus ticketStatus, Money ticketCost, Human human) {
		this.ticketNumber = (int) ticketNumber;
		this.departureCity = departureCity;
		this.arrivalCity = arrivalCity;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.ticketStatus = ticketStatus;
		this.ticketCost = ticketCost;
		this.human = human;
	}

	public int getTicketNumber() {
		return ticketNumber;
	}

	void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Money getTicketCost() {
		return ticketCost;
	}

	void setTicketCost(Money ticketCost) {
		this.ticketCost = ticketCost;
	}

	public Human getHuman() {
		return this.human;
	}

	void setHuman(Human human) {
		this.human = human;
	}
}
