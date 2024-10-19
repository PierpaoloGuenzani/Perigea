package it.perigea.marketdatastorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * An application to store and retrieve from the market 
 */
@SpringBootApplication
public class MarketDataStorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketDataStorerApplication.class, args);
	}

}
