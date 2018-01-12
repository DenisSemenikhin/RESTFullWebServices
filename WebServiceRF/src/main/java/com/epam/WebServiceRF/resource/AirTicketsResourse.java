package com.epam.WebServiceRF.resource;

import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.epam.WebServiceRF.model.FlyTicket;
import com.epam.WebServiceRF.model.Order;
import com.epam.WebServiceRF.services.AirTicketsServiceImpl;

@Path("/airtickets")
public class AirTicketsResourse {

	AirTicketsServiceImpl airTicketsService = new AirTicketsServiceImpl();

	@GET
	@Path("/{flyTicketId}")
	@Produces(MediaType.APPLICATION_XML)
	public FlyTicket getTicketByNumber(@PathParam("flyTicketId") String flyTicketId) throws ParseException {
		return airTicketsService.getTicketByNumber(flyTicketId);
	}

	@PUT
	@Path("/payfor")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean payForFlyTicket(String flyTicketId) throws ParseException {
		return airTicketsService.payForFlyTicket(flyTicketId);
	}

	@DELETE
	@Path("/{flyTicketId}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean deleteTicketById(@PathParam("flyTicketId") String flyTicketId) throws ParseException {
		return airTicketsService.deleteTicketById(flyTicketId);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public int bookFlyTicket(Order order) throws ParseException {
		return airTicketsService.bookFlyTicket(order);
	}

}
