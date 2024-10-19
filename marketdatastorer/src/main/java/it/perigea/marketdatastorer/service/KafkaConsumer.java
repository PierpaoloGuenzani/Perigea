package it.perigea.marketdatastorer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.perigea.marketdata.common.ExchangeRateDTO;
import it.perigea.marketdatastorer.model.ExchangeRate;
import it.perigea.marketdatastorer.model.ExchangeRateMapper;

@Service
public class KafkaConsumer
{
	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ExchangeRateMapper mapper;
	
    @KafkaListener(topics = "${spring.kafka.daily.topic}", groupId = "${spring.kafka.daily.group}")
    public void consume(String message)
    {
    	try
    	{
    		logger.info(message);
			ExchangeRateDTO exchangeRate = objectMapper.readValue(message, ExchangeRateDTO.class);
			ExchangeRate rate = mapper.mapToEntity(exchangeRate);
			storageService.saveExchangeRate(rate);
			logger.info("Exchange rate: "+exchangeRate.getSymbol()+" saved on mongoDB");
		}
    	catch(JsonMappingException e)
    	{
			e.printStackTrace();
		}
    	catch(JsonProcessingException e)
    	{
			e.printStackTrace();
		}
    }
}
