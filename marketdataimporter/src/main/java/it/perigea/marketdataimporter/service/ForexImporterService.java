package it.perigea.marketdataimporter.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.perigea.marketdataimporter.exception.DateFormatException;
import it.perigea.marketdataimporter.exception.InvalideDateException;

import it.perigea.marketdata.common.DailyResponseDTO;

/**
 * A service to retrieve forex market data  
 */
@Service
public class ForexImporterService
{
	private final Logger logger = LoggerFactory.getLogger(ForexImporterService.class);
	
	@Autowired
	private PolygonService polygonService;
	
	/**
	 * A function to retrieve and save the data for the exchange rates for a given data
	 * @param date - the date of the day we want to retrieve (format: yyyy-mm-dd)
	 * @return DailyResponse - the data of the given date
	 * @throws InvalideDateException - is thrown when the date is not valid (ex: 31 February)
	 * @throws DateFormatException - is thrown when the data format is incorrect
	 * @throws Exception - is thrown when we can't retrieve the data 
	 */
	public DailyResponseDTO getDaily(String date) throws Exception, InvalideDateException, DateFormatException
	{
		LocalDate parsedDate;
		try
		{
			parsedDate = LocalDate.parse(date);
		}
		catch(DateTimeParseException ex)
		{
			throw new DateFormatException("The date must be in format yyyy-mm-dd!");
		}
		if(parsedDate.isAfter(LocalDate.now()))
		{
			throw new InvalideDateException("The date must be a past date!", parsedDate);
		}
		DailyResponseDTO dailyResponse = polygonService.getDailyFromPolygon(parsedDate);
		if(dailyResponse == null)
		{
			throw new Exception("Unknown Exception");
		}
		
		return dailyResponse;
	}
}
