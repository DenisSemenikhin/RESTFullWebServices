package com.epam.WebServiceRF.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.epam.WebServiceRF.model.FlyInfo;
import com.epam.WebServiceRF.model.Money;

public class FlyInfoManager {

	private final static String PATH_TO_JSON_FLYINFO = "C:/DATABASE/flyinfo.json";

	private static JSONObject flyIdJson;
	private static JSONObject flyDateJsonObject;

	/*
	 * Основной метод получения информации о рейсе по ID рейса, вызывается утилита
	 * получения json объекта всех рейсов, проверяется наличие рейса и в случае
	 * успеха извлекается json объект конкретного рейса, вызывается вспомогательный
	 * метод получения FlуInfo объекта с помощью метода getFlyInfoFromJSON.
	 * Возвращается истина в случае успеха и ложь в обратном
	 */

	public static FlyInfo getFlyInfo(String flyId) throws ParseException {
		JSONObject allFlyInfoJson = DaoUtilits.getJsonObject(PATH_TO_JSON_FLYINFO);
		if (allFlyInfoJson.containsKey(flyId)) {
			JSONObject flyIdJson = (JSONObject) allFlyInfoJson.get(flyId);
			FlyInfo flyInfo = getFlyInfoFromJSON(flyIdJson);
			return flyInfo;
		} else
			return null;
	}

	/*
	 * Вспомогательный метод получения информации о рейсе из json, принимается на
	 * вход json объект, содержащий информацию о рейсе возвращается объект FlyInfo
	 * из которого можно извелечь информацию о полете
	 */
	static FlyInfo getFlyInfoFromJSON(JSONObject flyIdJson) throws java.text.ParseException {
		FlyInfo flyInfo = new FlyInfo();
		flyInfo.setFlyId((String) flyIdJson.get(DaoConst.FLY_ID));
		flyInfo.setDepartureCity((String) flyIdJson.get(DaoConst.DEPARTURE_CITY));
		flyInfo.setArrivalCity((String) flyIdJson.get(DaoConst.ARRIVAL_CITY));

		BigInteger flyInfoCostAmmount = BigInteger.valueOf((Long) flyIdJson.get(DaoConst.TICKET_COST_AMMOUNT));
		Currency flyInfoCostCurrency = Currency.getInstance((String) flyIdJson.get(DaoConst.TICKET_COST_CURRENCY));
		Money flyInfoCost = new Money(flyInfoCostAmmount, flyInfoCostCurrency);
		flyInfo.setCost(flyInfoCost);

		String departureDateString = (String) flyIdJson.get(DaoConst.DEPARTURE_DATE);
		Date departureDate = DaoUtilits.stringToDate(departureDateString, DaoConst.FLY_DATE_FORMAT);
		flyInfo.setDepartureDate(departureDate);

		String arrivalDateString = (String) flyIdJson.get(DaoConst.ARRIVAL_DATE);
		Date arrivalDate = DaoUtilits.stringToDate(arrivalDateString, DaoConst.FLY_DATE_FORMAT);
		flyInfo.setArrivalDate(arrivalDate);
		return flyInfo;
	}

	public static HashMap getFlyInfoHashMap() {
		flyDateJsonObject = DaoUtilits.getJsonObject(PATH_TO_JSON_FLYINFO);
		HashMap<String, String> flydateMap = (HashMap) flyDateJsonObject;
		return flydateMap;
	}

}
