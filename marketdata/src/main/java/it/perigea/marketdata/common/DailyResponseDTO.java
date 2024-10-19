package it.perigea.marketdata.common;

import java.util.List;

/**
 * A class that map the Polygon response
 */
public class DailyResponseDTO
{
	private boolean adjusted;
	private int queryCount;
	private List<ExchangeRateDTO> results;
	
	public boolean isAdjusted()
	{
		return adjusted;
	}
	
	public void setAdjusted(boolean adjusted)
	{
		this.adjusted = adjusted;
	}
	
	public int getQueryCount()
	{
		return queryCount;
	}
	
	public void setQueryCount(int queryCount)
	{
		this.queryCount = queryCount;
	}
	
	public List<ExchangeRateDTO> getResults()
	{
		return results;
	}
	
	public void setResults(List<ExchangeRateDTO> results)
	{
		this.results = results;
	}
	
}
