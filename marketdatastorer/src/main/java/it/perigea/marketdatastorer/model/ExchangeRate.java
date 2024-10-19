package it.perigea.marketdatastorer.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("exchangerates")
@CompoundIndex(name = "symbol_date_index", def = "{'symbol' : 1, 'date' : 1}", unique = true)
public class ExchangeRate
{
	@Indexed(name = "symbol_index")
	private String	symbol;					//T
	private Double	closePrice;				//c
	private Double	highestPrice;			//h
	private Double	lowestPrice;			//l
	private Long	numberOfTransaction;	//n
	private Double	openingPrice;			//o
	@Indexed(name = "date_index")
	private LocalDate date;
	private Long 	tradingVolume;			//v
	private Double	weightedAverage;		//vw
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	
	public Double getClosePrice()
	{
		return closePrice;
	}
	
	public void setClosePrice(Double closePrice)
	{
		this.closePrice = closePrice;
	}
	
	public Double getHighestPrice()
	{
		return highestPrice;
	}
	
	public void setHighestPrice(Double highestPrice)
	{
		this.highestPrice = highestPrice;
	}
	
	public Double getLowestPrice()
	{
		return lowestPrice;
	}
	
	public void setLowestPrice(Double lowestPrice)
	{
		this.lowestPrice = lowestPrice;
	}
	
	public Long getNumberOfTransaction()
	{
		return numberOfTransaction;
	}
	
	public void setNumberOfTransaction(Long numberOfTransaction)
	{
		this.numberOfTransaction = numberOfTransaction;
	}
	
	public Double getOpeningPrice()
	{
		return openingPrice;
	}
	
	public void setOpeningPrice(Double openingPrice)
	{
		this.openingPrice = openingPrice;
	}
	
	public Long getTradingVolume()
	{
		return tradingVolume;
	}
	
	public void setTradingVolume(Long tradingVolume)
	{
		this.tradingVolume = tradingVolume;
	}
	
	public Double getWeightedAverage()
	{
		return weightedAverage;
	}
	
	public void setWeightedAverage(Double weightedAverage)
	{
		this.weightedAverage = weightedAverage;
	}
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
