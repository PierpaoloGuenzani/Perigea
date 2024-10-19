package it.perigea.marketdataimporter.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.marketdataimporter.exception.DateFormatException;
import it.perigea.marketdataimporter.exception.InvalideDateException;
import it.perigea.marketdataimporter.service.DataImporterService;

/**
 * A Controller class for the data importer REST API
 */
@CrossOrigin
@RestController
public class DataImporterController
{
	private final Logger logger = LoggerFactory.getLogger(DataImporterController.class);
	
	@Autowired
	private DataImporterService dataImporter;
	
	/**
	 * A get request for retrieving and storing all the exchange rate
	 * @param date - the date of the exchange rates to retrieve
	 * @return ResponseEntity - the HTTP response
	 * @throws Exception 
	 */
	@GetMapping("/forexDaily/{date}")
	public ResponseEntity<String> getDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) throws Exception
	{
		logger.info("Received a forex daily request whit date :"+date);
		return ResponseEntity.ok(dataImporter.getDaily(date));
	}
	
	@ExceptionHandler(value = {DateFormatException.class, InvalideDateException.class})
	public ResponseEntity<String> myCustomExceptionHandler(Exception e)
	{
		logger.error("DATA IMPORTER EXCEPTION HANDLER: "+e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(Exception e)
	{
		logger.error("DATA IMPORTER EXCEPTION HANDLER: "+e.getMessage());
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
}
