package it.perigea.marketdata.common;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class that contains all the data for a given exchange rate
 */
public class ExchangeRateDTO
{
	@JsonProperty("symbol")
	@JsonAlias({"T"})
	private String	symbol;					//T
	
	@JsonProperty("closePrice")
	@JsonAlias({"c"})
	private Double	closePrice;				//c
	
	@JsonProperty("highestPrice")
	@JsonAlias({"h"})
	private Double	highestPrice;			//h
	
	@JsonProperty("lowestPrice")
	@JsonAlias({"l"})
	private Double	lowestPrice;			//l
	
	@JsonProperty("numberOfTransaction")
	@JsonAlias({"n"})
	private Long	numberOfTransaction;	//n
	
	@JsonProperty("openingPrice")
	@JsonAlias({"o"})
	private Double	openingPrice;			//o
	
	@JsonProperty("timeStamp")
	@JsonAlias({"t"})
	private Long 	timeStamp;				//t Unix Msec timestamp
	
	@JsonProperty("tradingVolume")
	@JsonAlias({"v"})
	private Long 	tradingVolume;			//v
	
	@JsonProperty("weightedAverage")
	@JsonAlias({"vw"})
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
	
	public Long getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(Long timeStamp)
	{
		this.timeStamp = timeStamp;
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
	
}
