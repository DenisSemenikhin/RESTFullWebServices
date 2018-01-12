package com.epam.WebClientRF.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utilits {
	public final static String FLY_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public final static String FLY_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public final static String BIRTH_DATE_FORMAT = "yyyy-MM-dd";

	public static Date stringToDate(String dateString, String patern) throws java.text.ParseException {
		Date date;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patern);
		date = simpleDateFormat.parse(dateString);
		return date;
	}

	public static String datetoString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static XMLGregorianCalendar datetoXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gcDate = new GregorianCalendar();
		gcDate.setTime(date);
		XMLGregorianCalendar xmlGCDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcDate);
		return xmlGCDate;
	}

	public static Date xmlGregorianCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar)
			throws DatatypeConfigurationException {
		Date date = xmlGregorianCalendar.toGregorianCalendar().getTime();
		return date;
	}

}
