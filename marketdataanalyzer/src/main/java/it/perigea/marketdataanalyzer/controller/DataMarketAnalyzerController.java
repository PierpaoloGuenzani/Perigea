package it.perigea.marketdataanalyzer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.marketdataanalyzer.service.AnalyzerService;
import it.perigea.marketdataanalyzer.service.ExchangeRateEstimation;

@RestController
@RequestMapping("/analyzer")
public class DataMarketAnalyzerController {
	
	private Logger logger = LoggerFactory.getLogger(DataMarketAnalyzerController.class);
	
	@Autowired
	private AnalyzerService analyzerService;
	
	@GetMapping("/exchange-rates/{symbol}")
	public ResponseEntity<List<ExchangeRateEstimation>> 
	getExchangeRateEstimationList(@PathVariable String symbol)
	{
		logger.info("DATA MARKET ANALYZER: estimation request of symbol:"+symbol);
		return ResponseEntity.ok(analyzerService.exchangeRateEstimation(symbol));
	}

	@ExceptionHandler
	public ResponseEntity<String> exceptionHandler(Exception e)
	{
		logger.info("DATA MARKET ANALYZER: EXCEPTION HANDLER: "+e.getMessage());
		return ResponseEntity.internalServerError().body("UNKNOWN ERROR: "+e.getMessage());
	}
}
