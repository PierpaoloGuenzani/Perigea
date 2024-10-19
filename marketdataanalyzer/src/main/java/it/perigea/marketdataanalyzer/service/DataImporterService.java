package it.perigea.marketdataanalyzer.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.perigea.marketdata.common.ExchangeRate;

@Service
public class DataImporterService {
	
	@Value("${marketDataStorer.host}")
	private String host;
	@Value("${marketDataStorer.port}")
	private int port;
	
	public ExchangeRate[] importExchangeRate(String symbol)
	{
		WebClient webClient = WebClient.create();
		
		ExchangeRate[] array = webClient.get().uri(
				uriBuilder ->
				uriBuilder.scheme("http").host(host).port(port)
				.path("/storage/exchange-rates/historical/{symbol}")
				.build(symbol)
				)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(ExchangeRate[].class)
		.onErrorResume(e -> {throw new RuntimeException("Storage Service Error");})
		.block();
		return array;
	}
}
