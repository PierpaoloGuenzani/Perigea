package it.perigea.marketdataanalyzer.service;

import java.time.LocalDate;

public class ExchangeRateEstimation {
	
	private String symbol;
	private Double price;
	private LocalDate date;
	
	public ExchangeRateEstimation(String symbol, Double price, LocalDate date) {
		super();
		this.symbol = symbol;
		this.price = price;
		this.date = date;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
}
