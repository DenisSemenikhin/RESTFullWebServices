package com.epam.WebClientRF.control;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;

import com.epam.WebClientRF.model.FlyTicket;
import com.epam.WebClientRF.model.Human;
import com.epam.WebClientRF.model.Order;
import com.epam.WebClientRF.model.Utilits;
import com.epam.WebClientRF.visual.ConsoleView;

public class Start {

	private Human human = null;
	private FlyTicket flyTicket;
	private String REST_SERVICE_URL = "http://localhost:8080/WebServiceRF/webapi/airtickets";
	private Client client;

	private void init() {
		this.client = ClientBuilder.newClient();
	}

	public Start() {
		this.human = null;
		this.flyTicket = null;
		this.client = client;
		init();
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public FlyTicket getTicket() {
		return flyTicket;
	}

	public void setTicket(FlyTicket ticket) {
		this.flyTicket = ticket;
	}

	public static void main(String[] args)
			throws DatatypeConfigurationException, IOException, ParseException, ParseException {
		Start startInfo = new Start();
		ConsoleView consoleView = new ConsoleView(startInfo);
		consoleView.showMainMenu();
	}

	public int bookFlyTicket(String flyID) throws ParseException, IOException {
		Order order = new Order();
		order.setHuman(this.human);
		order.setFlyID(flyID);
		int flyTicketID = client.target(REST_SERVICE_URL).request(MediaType.TEXT_PLAIN)
				.post(Entity.entity(order, MediaType.APPLICATION_XML), Integer.class);
		this.getFlyTicketByID(String.valueOf(flyTicketID));
		return flyTicketID;
	}

	public FlyTicket getFlyTicketByID(String flyTicketID) throws ParseException, IOException {
		FlyTicket flyTicket = client.target(REST_SERVICE_URL).path("/{userid}").resolveTemplate("userid", flyTicketID)
				.request(MediaType.APPLICATION_XML).get(FlyTicket.class);
		setTicket(flyTicket);
		return this.flyTicket;
	}

	public boolean paidForTicketByID(String flyTicketID) throws ParseException, IOException {
		boolean paidfor = client.target(REST_SERVICE_URL).path("/payfor").request(MediaType.TEXT_PLAIN)
				.put(Entity.entity(flyTicketID, MediaType.TEXT_PLAIN), boolean.class);
		this.getFlyTicketByID(flyTicketID);
		return paidfor;
	}

	public boolean deleteFlyTicketByID(String flyTicketID) throws ParseException {
		Boolean callResult = client.target(REST_SERVICE_URL).path("/{userid}").resolveTemplate("userid", flyTicketID)
				.request(MediaType.TEXT_PLAIN).delete(Boolean.class);
		if (callResult) {
			return true;
		}
		return false;
	}

	public void acceptPersonalInformation(String fName, String lName, String patronomyc, String birthDateString)
			throws ParseException, DatatypeConfigurationException {
		Human customer = new Human();
		customer.setfName(fName);
		customer.setlName(lName);
		customer.setPatronomyc(patronomyc);
		Date birthDate = Utilits.stringToDate(birthDateString, Utilits.BIRTH_DATE_FORMAT);
		customer.setBirthDate(birthDate);
		setHuman(customer);
	}

}
