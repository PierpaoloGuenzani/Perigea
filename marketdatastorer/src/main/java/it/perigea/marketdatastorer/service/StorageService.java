package it.perigea.marketdatastorer.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mongodb.MongoWriteException;

import it.perigea.marketdatastorer.model.ExchangeRate;
import it.perigea.marketdatastorer.mongo.ExchangeRateRepository;

/**
 * A class to retrieve data from Mongodb
 */
@Service
public class StorageService
{
	private Logger logger = LoggerFactory.getLogger(StorageService.class);

	@Autowired
	private ExchangeRateRepository repository;
	

	/**
	 * A function to retrieve a list of the exchange rates by a given date 
	 * @param date - the date of the data to retrieve (format yyyy-mm-dd)
	 * @return List<ExchangeRate> - a list of exchange rate found
	 * @throws IllegalArgumentException - in case the date is not valid
	 * @throws Exception - in case some unknown error
	 */
	public List<ExchangeRate> getExchangeRatesByDate(LocalDate date)
	{
		checkValidDate(date);
		List<ExchangeRate> rates = repository.findExchangeRatesByDate(date);
		logger.info("Retrieved data from mondo! number of data: "+rates.size());
		return rates;
	}

	/**
	 * A function to retrieve an exchange rate by a given date and symbol
	 * @param date - the date of the date to retrieve (format yyyy-mm-dd)
	 * @param symbol - the symbol of the given exchange rate (ex. C:EURUSD)
	 * @return List<ExchangeRate> - the list of exchange rate found
	 * @throws IllegalArgumentException - in case the date is not valid
	 * @throws Exception - in case some unknown error
	 */
	public ExchangeRate getExchangeRateByDateAndSymbol(LocalDate date, String symbol)
	{
		checkValidDate(date);
		checkValidSymbol(symbol);
		ExchangeRate rate = repository.findExchangeRate(date, symbol);
		logger.info("Retrived exchande rate from mongo! exchange rate: "+rate);
		return rate;
	}

	/**
	 * A function to retrieve the historical data of a given exchange rate
	 * @param symbol - the symbol of the given exchange rate (ex. C:EURUSD)
	 * @return List<ExchangeRate> - the list of exchange rate found
	 * @throws Exception - in case some unknown error
	 */
	public List<ExchangeRate> getHistoricalExchangeRates(String symbol,
			LocalDate startingDate, LocalDate endingDate)
	{
		checkValidSymbol(symbol);
		List<ExchangeRate> rates;
		if(null == startingDate && null == endingDate)
		{
			rates = repository.findExchangeRatesBySymbol(symbol);
			logger.info("Retrieved data from mondo! number of data: "+rates.size());
			return rates;
		}
		if(null == startingDate && null != endingDate)
		{
			checkValidDate(endingDate);
			rates = repository.findExchangeRatesBySymbol(symbol, endingDate);
			logger.info("Retrieved data from mondo! number of data: "+rates.size());
			return rates;
		}
		if(null != startingDate && null == endingDate)
			endingDate = LocalDate.now();
		rates = repository.findExchangeRatesBySymbol(symbol, startingDate, endingDate);
		logger.info("Retrieved data from mondo! number of data: "+rates.size());
		return rates;
	}
	
	/**
	 * A function to save the data of an ExchangeRate
	 * @param exchangeRate - not null
	 */
	public void saveExchangeRate(ExchangeRate exchangeRate)
	{
		if(exchangeRate == null) throw new IllegalArgumentException("Exchange rate can not be null");
		try
		{
			repository.save(exchangeRate);
			logger.info("Saved exchange rate: "+exchangeRate.getSymbol()+" in date: "+exchangeRate.getDate());
		}
		catch(org.springframework.dao.DuplicateKeyException e)
		{
			//info o error
			logger.info("Duplicate exchange rate, symbol : "+exchangeRate.getSymbol()
			+" date : "+exchangeRate.getDate());
		}
		catch(MongoWriteException e)
		{
			logger.error("Writing error: impossible to wirte "+exchangeRate.getSymbol());
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
	}
	
	private void checkValidDate(LocalDate date)
	{
		try
		{
			if(date.isAfter(LocalDate.now()))
			{
				logger.error("Storage service: the date has been rejected: can not be a future date");
				throw new IllegalArgumentException("Invalid date: the date must be a past date!");
			}
		}
		catch (DateTimeParseException e)
		{
			logger.error("Storage service: Invalid format: format must be yyyy-MM-dd");
			throw new IllegalArgumentException("Invalid format: format must be yyyy-MM-dd!");
		}
		logger.info("The date has been validated! date: "+date);
	}
	
	private void checkValidSymbol(String symbol)
	{
		if(symbol.length()!= 8)
		{
			logger.error("Storage service: the symbol has been rejected: symbol must be of lenght 8!");
			throw new IllegalArgumentException("Symbol must be 8 character in format: C:USDEUR");
		}
		if(!symbol.contains("C:"))
		{
			logger.error("Storage service: the symbol has been rejected: missing C:");
			throw new IllegalArgumentException("Symbol must contain :, ex. C:USDEUR");
		}
		logger.info("The Symbol has been validated! symbol: "+symbol);
	}

	public List<ExchangeRate> getAllExchangeRate()
	{
		return repository.findAll();
	}
}
