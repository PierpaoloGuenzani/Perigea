package it.perigea.marketdataimporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * An application for retrieve data from the stock market 
 */
@SpringBootApplication
public class MarketDataImporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketDataImporterApplication.class, args);
	}

}
