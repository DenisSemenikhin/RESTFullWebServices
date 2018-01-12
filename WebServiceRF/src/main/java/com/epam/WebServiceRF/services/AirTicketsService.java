package com.epam.WebServiceRF.services;

import java.text.ParseException;

import com.epam.WebServiceRF.model.FlyTicket;
import com.epam.WebServiceRF.model.Order;

public interface AirTicketsService {

	public int bookFlyTicket(Order order) throws ParseException;

	FlyTicket getTicketByNumber(String idTicket) throws ParseException;

	public boolean payForFlyTicket(String idTicket) throws ParseException;

	public boolean deleteTicketById(String idTicket) throws ParseException;

}
