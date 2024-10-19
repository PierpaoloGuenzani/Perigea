package it.perigea.marketdataimporter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.perigea.marketdata.common.DailyResponseDTO;
import it.perigea.marketdata.common.ExchangeRateDTO;


/**
 * A service that stream the input data to a streaming platform (default is Kafka)
 */
@Service
public class StreamService
{
	private Logger logger = LoggerFactory.getLogger(StreamService.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${spring.kafka.daily.topic}")
	private String dailyTopicName;
	
	/**
	 * A method to stream the dailyData to Kafka
	 * @param dailyResponse - the daily data to send
	 */
	public void streamDailyOnKafka(DailyResponseDTO dailyResponse)
	{
		logger.info("Starting to stream daily data to: "+dailyTopicName);
		ObjectMapper mapper = new ObjectMapper();
		for(ExchangeRateDTO er : dailyResponse.getResults())
		{
			try
			{
				kafkaTemplate.send(dailyTopicName, er.getSymbol(), mapper.writeValueAsString(er));
			}
			catch(JsonProcessingException e)
			{
				logger.error("Error to stream: "+er.getSymbol());
			}
		}
	}
}
