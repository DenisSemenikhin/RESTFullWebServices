package com.epam.WebServiceRF.services;

import java.io.IOException;
import java.text.ParseException;

import com.epam.WebServiceRF.dao.FlyInfoManager;
import com.epam.WebServiceRF.dao.TicketManager;
import com.epam.WebServiceRF.model.FlyInfo;
import com.epam.WebServiceRF.model.FlyTicket;
import com.epam.WebServiceRF.model.Human;
import com.epam.WebServiceRF.model.Order;
import com.epam.WebServiceRF.model.TicketStatus;

public class AirTicketsServiceImpl implements AirTicketsService {

	@Override
	public int bookFlyTicket(Order order) throws ParseException {
		String flyId = order.getFlyID();
		Human human = order.getHuman();
		FlyInfo flyInfo = FlyInfoManager.getFlyInfo(flyId);
		if (flyInfo != null) {
			FlyTicket flyTicket = null;
			try {
				flyTicket = TicketManager.createNewTicket(human, flyInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			TicketManager.writeTicketInfo(flyTicket);
			return flyTicket.getTicketNumber();
		} else {
			return -1;
		}
	}

	@Override
	public FlyTicket getTicketByNumber(String idTicket) throws ParseException {
		FlyTicket flyTicket = TicketManager.getFlyTicket(idTicket);
		if (flyTicket != null) {
			return flyTicket;
		} else {
			return null;
		}
	}

	@Override
	public boolean payForFlyTicket(String idTicket) throws ParseException {
		FlyTicket flyTicket = TicketManager.getFlyTicket(idTicket);
		if ((flyTicket != null) && (flyTicket.getTicketStatus() == TicketStatus.booked)) {
			flyTicket.setTicketStatus(TicketStatus.paidfor);
			TicketManager.writeTicketInfo(flyTicket);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteTicketById(String idTicket) throws ParseException {
		FlyTicket flyTicket = TicketManager.getFlyTicket(idTicket);
		if ((flyTicket != null) && (flyTicket.getTicketStatus() == TicketStatus.booked)) {
			TicketManager.removeTicket(idTicket);
			return true;
		} else {
			return false;
		}
	}

}