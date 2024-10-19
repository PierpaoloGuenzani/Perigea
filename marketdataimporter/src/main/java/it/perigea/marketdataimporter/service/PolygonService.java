package it.perigea.marketdataimporter.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.perigea.marketdata.common.DailyResponseDTO;

/**
 * A wrapper to Polygon.io REST API
 */
@Service
public class PolygonService implements PolygonInterface
{
	
	@Value("${authentication}")
	private String authentication;
	
	/**
	 * A wrapper to polygon.io grouped daily API
	 * @param date, the date in format YYYY-mm-dd
	 * @return DailyResponse, a java object that wrap the JSON response
	 */
	public DailyResponseDTO getDailyFromPolygon(LocalDate date)
	{
		WebClient client = WebClient.create();
		
		DailyResponseDTO response = client.get().uri(
				uriBuilder -> 
				uriBuilder
				.scheme(POLYGON_SCHEME)
				.host(POLYGON_HOST)
				.path(POLYGON_DAILY_PATH)
				.queryParam("adjusted", true)
				.queryParam("apiKey", authentication)
				.build(date.toString())
				)
		.accept(MediaType.APPLICATION_JSON)
		.retrieve()
		.bodyToMono(DailyResponseDTO.class)
		.onErrorResume(e -> {throw new RuntimeException("Polygon server error");})
		.block();
		
		return response;
	}
}
