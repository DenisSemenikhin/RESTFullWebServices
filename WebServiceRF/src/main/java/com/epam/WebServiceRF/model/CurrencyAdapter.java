package com.epam.WebServiceRF.model;

import java.util.Currency;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CurrencyAdapter extends XmlAdapter<String, Currency> {

	/*
	 * Java => XML Given the unmappable Java object, return the desired XML
	 * representation.
	 */
	public String marshal(Currency val) throws Exception {
		return val.toString();
	}

	/*
	 * XML => Java Given an XML string, use it to build an instance of the
	 * unmappable class.
	 */
	public Currency unmarshal(String val) throws Exception {
		return Currency.getInstance(val);
	}
}
