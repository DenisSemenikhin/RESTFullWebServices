package com.epam.WebServiceRF.dao;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Currency;
import java.util.Date;

import org.json.simple.JSONObject;

import com.epam.WebServiceRF.model.FlyInfo;
import com.epam.WebServiceRF.model.FlyTicket;
import com.epam.WebServiceRF.model.Human;
import com.epam.WebServiceRF.model.Money;
import com.epam.WebServiceRF.model.TicketStatus;

public class TicketManager {

	private final static String PATH_TO_JSON_TICKETS = "C:/DATABASE/tickets.json";
	private final static String PATH_TO_JSON_TICKETS_NUMBERS = "C:/DATABASE/ticketNumbers.json";

	/*
	 * Основной метод бронирования билета, принимает на вход 2-а объекта Заказчика и
	 * Информацию о рейсе. Возвращает билет
	 */
	public static FlyTicket createNewTicket(Human human, FlyInfo flyInfo) throws IOException {
		FlyTicket ticket;
		Date departureDate = flyInfo.getDepartureDate();
		Date arrivalDate = flyInfo.getArrivalDate();
		String departureCity = flyInfo.getDepartureCity();
		String arrivalCity = flyInfo.getArrivalCity();
		Money ticketCost = flyInfo.getCost();
		TicketStatus ticketStatus = TicketStatus.booked;
		long ticketNumber;
		ticketNumber = getUniqueTicketNumber();
		ticket = new FlyTicket(ticketNumber, departureCity, arrivalCity, departureDate, arrivalDate, ticketStatus,
				ticketCost, human);
		return ticket;
	}

	/* Записывает билет в json, предварительно удаляет с аналогичным номером т.к. */
	public static void writeTicketInfo(FlyTicket ticket) {
		removeTicket(String.valueOf(ticket.getTicketNumber()));
		try {
			JSONObject ticketInfoJson = new JSONObject();
			ticketInfoJson.put(DaoConst.TICKET_NUMBER, ticket.getTicketNumber());
			ticketInfoJson.put(DaoConst.DEPARTURE_CITY, ticket.getDepartureCity());
			ticketInfoJson.put(DaoConst.ARRIVAL_CITY, ticket.getArrivalCity());
			String departureDate = DaoUtilits.datetoString(ticket.getDepartureDate(), DaoConst.FLY_DATE_FORMAT);
			ticketInfoJson.put(DaoConst.DEPARTURE_DATE, departureDate);
			String arrivalDate = DaoUtilits.datetoString(ticket.getArrivalDate(), DaoConst.FLY_DATE_FORMAT);
			ticketInfoJson.put(DaoConst.ARRIVAL_DATE, arrivalDate);
			ticketInfoJson.put(DaoConst.TICKET_STATUS, ticket.getTicketStatus().toString());
			ticketInfoJson.put(DaoConst.TICKET_COST_AMMOUNT, ticket.getTicketCost().getAmount());
			ticketInfoJson.put(DaoConst.TICKET_COST_CURRENCY, ticket.getTicketCost().getCurrency().getCurrencyCode());
			ticketInfoJson.put(DaoConst.FIRST_NAME, ticket.getHuman().getfName());
			ticketInfoJson.put(DaoConst.SECOND_NAME, ticket.getHuman().getlName());
			ticketInfoJson.put(DaoConst.PATRONOMIC, ticket.getHuman().getPatronomyc());
			String birthDate = DaoUtilits.datetoString(ticket.getHuman().getBirthDate(), DaoConst.BIRTH_DATE_FORMAT);
			ticketInfoJson.put(DaoConst.BIRTH_DATE, birthDate);
			JSONObject ticketID = DaoUtilits.getJsonObject(PATH_TO_JSON_TICKETS);
			ticketID.put(ticket.getTicketNumber(), ticketInfoJson);
			DaoUtilits.writeJsonToFile(ticketID, PATH_TO_JSON_TICKETS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* Удаление билета из json */
	public static boolean removeTicket(String ticketNumber) {
		JSONObject ticketID = DaoUtilits.getJsonObject(PATH_TO_JSON_TICKETS);
		if (ticketID.containsKey(ticketNumber)) {
			ticketID.remove(ticketNumber);
			DaoUtilits.writeJsonToFile(ticketID, PATH_TO_JSON_TICKETS);
			return true;
		} else
			return false;
	}

	/*
	 * Основной метод получения информации о билете по ID билета , вызывается
	 * утилита получения json объекта всех билетов, проверяется наличие билета и в
	 * случае успеха извлекается json объект конкретного билета, вызывается
	 * вспомогательный метод получения FlуTicket объекта с помощью метода
	 * getFlyTicketFromJson. Возвращается истина в случае успеха и ложь в обратном
	 */
	public static FlyTicket getFlyTicket(String TicketID) throws ParseException {
		JSONObject ticketID = DaoUtilits.getJsonObject(PATH_TO_JSON_TICKETS);
		if (ticketID.containsKey(TicketID)) {
			JSONObject ticketInfoJson = (JSONObject) ticketID.get(TicketID);
			FlyTicket flyTicket = getFlyTicketFromJson(ticketInfoJson);
			return flyTicket;
		} else
			return null;
	}

	/*
	 * Вспомогательный метод получения информации о билете из json, принимается на
	 * вход json объект, содержащий информацию о билете возвращается объект
	 * FlyTicket
	 */
	private static FlyTicket getFlyTicketFromJson(JSONObject jsonObject) throws ParseException {
		long ticketNumber = (Long) jsonObject.get(DaoConst.TICKET_NUMBER);
		String departureCity = (String) jsonObject.get(DaoConst.DEPARTURE_CITY);
		String arrivalCity = (String) jsonObject.get(DaoConst.ARRIVAL_CITY);
		String departureDateString = (String) jsonObject.get(DaoConst.DEPARTURE_DATE);
		Date departureDate = DaoUtilits.stringToDate(departureDateString, DaoConst.FLY_DATE_PATTERN);
		String arrivalDateString = (String) jsonObject.get(DaoConst.ARRIVAL_DATE);
		Date arrivalDate = DaoUtilits.stringToDate(arrivalDateString, DaoConst.FLY_DATE_PATTERN);
		String ticketStatusString = (String) jsonObject.get(DaoConst.TICKET_STATUS);
		TicketStatus ticketStatus = TicketStatus.valueOf(ticketStatusString);
		Long ticketCostAmmountLong = (Long) jsonObject.get(DaoConst.TICKET_COST_AMMOUNT);
		BigInteger ticketCostAmmount = BigInteger.valueOf(ticketCostAmmountLong);
		Currency ticketCostCurrency = Currency.getInstance((String) jsonObject.get(DaoConst.TICKET_COST_CURRENCY));
		Money ticketCost = new Money(ticketCostAmmount, ticketCostCurrency);
		Human human = new Human();
		human.setfName((String) jsonObject.get(DaoConst.FIRST_NAME));
		human.setlName((String) jsonObject.get(DaoConst.SECOND_NAME));
		human.setPatronomyc((String) jsonObject.get(DaoConst.PATRONOMIC));
		String dateBirthdaySting = (String) jsonObject.get(DaoConst.BIRTH_DATE);
		human.setBirthDate(DaoUtilits.stringToDate(dateBirthdaySting, DaoConst.BIRTH_DATE_FORMAT));
		FlyTicket flyTicket = new FlyTicket(ticketNumber, departureCity, arrivalCity, departureDate, arrivalDate,
				ticketStatus, ticketCost, human);
		return flyTicket;
	}

	/*
	 * Вспомогательный метод получения уникального номера билета
	 */
	public static long getUniqueTicketNumber() throws IOException {
		long ticketNumber;

		JSONObject ticketNumbersJson = DaoUtilits.getJsonObject(PATH_TO_JSON_TICKETS_NUMBERS);
		if (ticketNumbersJson.containsKey("UniqueTicketNumber")) {
			ticketNumber = (Long) ticketNumbersJson.get("UniqueTicketNumber") + 1;
			ticketNumbersJson.put("UniqueTicketNumber", ticketNumber);
		} else {
			ticketNumber = 1;
			ticketNumbersJson.put("UniqueTicketNumber", ticketNumber);
		}
		DaoUtilits.writeJsonToFile(ticketNumbersJson, PATH_TO_JSON_TICKETS_NUMBERS);
		return ticketNumber;
	}

}
