package it.perigea.marketdatastorer.controller;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.marketdatastorer.model.ExchangeRate;
import it.perigea.marketdatastorer.service.StorageService;

/**
 * A Controller class for the REST API
 */
@CrossOrigin
@RestController
@RequestMapping("/storage")
public class DataStorerController
{
	private Logger logger = LoggerFactory.getLogger(DataStorerController.class);
	
	@Autowired
	private StorageService storageService;
	
	@GetMapping("/exchange-rates")
	public ResponseEntity<List<ExchangeRate>> getAll()
	{
		logger.info("Exchange Rates: request all exchange rate");
		return ResponseEntity.ok(storageService.getAllExchangeRate());
	}
	
	/**
	 * A function to retrieve all the exchange rates stored in DB for a given date 
	 * @param date - the date of the data to retrieve (format yyyy-MM-dd)
	 * @return ResponseEntity - an HTTP response containing a list of exchanged data in JSON format
	 */
	@GetMapping("/exchange-rates/{date}")
	public ResponseEntity<List<ExchangeRate>> getExchangeRatesByDate(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
	{
		logger.info("Exchange Rates request with date: "+date);
		return ResponseEntity.ok(storageService.getExchangeRatesByDate(date));
	}
	
	/**
	 * A function to retrieve the data of an exchange rate in a specific date 
	 * @param date - the date of the data to retrieve (format yyyy-MM-dd)
	 * @param symbol - the symbol of the data to retrieve (ex. C:USDEUR)
	 * @return ResponseEntity - an HTTP response containing an exchanged rate in JSON format
	 */
	@GetMapping("/exchange-rates/{date}/{symbol}")
	public ResponseEntity<ExchangeRate> getExchangeRateByDateAndSymbol(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
			@PathVariable String symbol)
	{
		logger.info("Exchange Rate request with date: "+date+" and symbol: "+symbol);
		return ResponseEntity.ok(storageService.getExchangeRateByDateAndSymbol(date, symbol));
	}
	
	/**
	 * A function to retrieve all stored historical data of a given exchange rate
	 * @param symbol - the symbol of the data to retrieve (ex. C:USDEUR)
	 * @return ResponseEntity - a list of exchanged data 
	 */
	@GetMapping("/exchange-rates/historical/{symbol}")
	public ResponseEntity<List<ExchangeRate>> getExchangeRatesBySymbol(@PathVariable String symbol,
			@RequestParam(required = false) String startingDate,
			@RequestParam(required = false) String endingDate)
	{
		logger.info("Exchange Rate historical data request with symbol: "+symbol);
		return ResponseEntity.ok(storageService.getHistoricalExchangeRates(symbol,
				parseDate(startingDate), parseDate(endingDate)));
	}
	
	/**
	 * A private function for converting a String into a LocalDate
	 * @param date - the date to parse
	 * @return LocalDate - the parsed date
	 */
	private LocalDate parseDate(String date)
	{
		if(null == date) return null;
		try
		{
			LocalDate parsedDate = LocalDate.parse(date);
			return parsedDate;
		}
		catch (DateTimeParseException e)
		{
			throw new IllegalArgumentException("Invalid format: format must be yyyy-mm-dd!");
		}
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException e)
	{
		logger.error("ILLEGAL ARGUMENT EXEPTION HANDLER: "+e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(Exception e)
	{
		logger.error("EXEPTION HANDLER: "+e.getMessage());
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
}
