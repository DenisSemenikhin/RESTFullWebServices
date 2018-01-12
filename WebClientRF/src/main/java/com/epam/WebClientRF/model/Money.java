package com.epam.WebClientRF.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Money")
@XmlAccessorType(XmlAccessType.NONE)
public class Money implements Serializable {

	@XmlElement(name = "amount")
	private BigInteger amount;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private Currency currency;
	@XmlElement(name = "currencyLiter")
	private String currencyLiter;

	public Money() {
	}

	public String getCurrencyLiter() {
		return currencyLiter;
	}

	public void setCurrencyLiter(String currencyLiter) {
		this.currencyLiter = currencyLiter;
	}

	public static int[] getCents() {
		return cents;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Money(BigInteger amount, Currency currency) {
		this.currency = currency;
		this.amount = amount;
		this.currencyLiter = this.currency.getCurrencyCode();
	}

	public BigInteger getAmount() {
		return amount;
	}

	/* standart for dollars and locals */
	static Money dollars(BigInteger ammount) {
		Money money = new Money(ammount, Currency.getInstance(Locale.US));
		return money;
	}

	static Money locale(BigInteger ammount) {
		Money money = new Money(ammount, Currency.getInstance(Locale.getDefault()));
		return money;
	}

	static final int[] cents = new int[] { 1, 10, 100 };

	/* return multiply for amount */
	private int centFactor() {
		return cents[currency.getDefaultFractionDigits()];
	}

	@Override
	public String toString() {
		return amount.divide(BigInteger.valueOf(cents[currency.getDefaultFractionDigits()])) + "."
				+ amount.remainder(BigInteger.valueOf(cents[currency.getDefaultFractionDigits()])) + " "
				+ getCurrency().getSymbol();
	}

	public Currency getCurrency() {
		return currency;
	}

	/* compare money obj with obj */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Money) {
			Money money_obj = (Money) obj;
			if ((this.amount == money_obj.amount) && (this.currency == money_obj.currency)) {
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	/* add two moneys objects */
	Money add(Money other, boolean exchange) throws MoneyExchangeException {
		/* not realised exchange method */
		if (!exchange) {
			throw new MoneyExchangeException("No exchange for curent operation");
		}
		return new Money(amount.add(other.amount), currency);

	}

	/* subtract from money objects other */
	Money subtract(Money other, boolean exchange) throws MoneyExchangeException {
		/* not realised exchange method */
		if (!exchange) {
			throw new MoneyExchangeException("No exchange for curent operation");
		}
		return new Money(amount.subtract(other.amount), currency);
	}

	/* compare money obj with other */
	int compareTo(Money other) {
		return amount.compareTo(other.amount);
	}

	/* compare amount Money obj with other */
	boolean greaterThan(Money other) {
		return (compareTo(other) > 0);
	}

	/* multiply amount money obj */
	Money multiply(long arg) {
		return new Money(amount.multiply(BigInteger.valueOf(arg)), currency);
	}

	/* separate amount of money for n parts */
	Money[] allocate(int n) {
		BigInteger remainder = amount.remainder(BigInteger.valueOf(n));
		BigInteger lowResAmount = amount.subtract(remainder).divide(BigInteger.valueOf(n));
		BigInteger highResAmount = lowResAmount.add(BigInteger.valueOf(1));
		Money lowResult = new Money(lowResAmount, currency);
		Money highResult = new Money(highResAmount, currency);
		Money[] results = new Money[n];
		for (int i = 0; i < remainder.intValue(); i++)
			results[i] = highResult;
		for (int i = remainder.intValue(); i < n; i++)
			results[i] = lowResult;
		return results;
	}

}
