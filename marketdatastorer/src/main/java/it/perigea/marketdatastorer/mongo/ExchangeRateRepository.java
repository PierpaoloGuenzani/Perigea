package it.perigea.marketdatastorer.mongo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import it.perigea.marketdatastorer.model.ExchangeRate;


public interface ExchangeRateRepository extends MongoRepository<ExchangeRate, String>
{

	@Query("{'date': ?0}")
	public List<ExchangeRate> findExchangeRatesByDate(LocalDate date);
	
	//riesce a convertirlo da solo un valore a Optional<valore>? NO NON RIESCE A CONVERTIRLO DA SOLO
	@Query("{'date': ?0, 'symbol':  ?1}")
	public ExchangeRate findExchangeRate(LocalDate date, String symbol);
	
	@Query("{'symbol': ?0}")
	public List<ExchangeRate> findExchangeRatesBySymbol(String symbol);
	
	@Query("{'symbol': ?0, 'date': { $lte: ?1} }")
	public List<ExchangeRate> findExchangeRatesBySymbol(String symbol, LocalDate endingDate);
	
	@Query("{'symbol': ?0, 'date': { $gte: ?1, $lte: ?2} }")
	public List<ExchangeRate> findExchangeRatesBySymbol(String symbol, LocalDate startiongDate, LocalDate endingDate);
}
