package com.epam.WebClientRF.model;

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
