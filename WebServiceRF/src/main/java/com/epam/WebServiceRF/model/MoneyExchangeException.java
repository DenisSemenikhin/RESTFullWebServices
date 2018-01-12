package com.epam.WebServiceRF.model;

public class MoneyExchangeException extends Exception {

	private String message;

	public MoneyExchangeException() {
	}

	MoneyExchangeException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
