package it.perigea.marketdataimporter.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.perigea.marketdata.common.DailyResponseDTO;
import it.perigea.marketdataimporter.logger.postgre.model.Request;
import it.perigea.marketdataimporter.logger.postgre.model.RequestState;
import it.perigea.marketdataimporter.logger.postgre.model.RequestType;
import it.perigea.marketdataimporter.logger.postgre.repository.RequestRepository;

/**
 * A service to import financial data from the market
 */
@Service
public class DataImporterService
{
	private final Logger logger = LoggerFactory.getLogger(DataImporterService.class);
	
	@Autowired
	private ForexImporterService forexImporterService;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private StreamService streamService;
	
	/**
	 * A function to retrieve the market data of the specified date
	 * @param date, the date of the market data
	 * @return String
	 */
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getDaily(LocalDate date) throws Exception
	{
		Request entry = new Request();
		entry.setRequestType(RequestType.DAILY);
		entry = requestRepository.save(entry);
		logger.info("Initialized daily request: state pending");
		
		DailyResponseDTO response;
		
		try
		{
			response = forexImporterService.getDaily(date.toString());
		}
		catch(Exception e)
		{
			entry.setRequestState(RequestState.ERROR);
			entry.endingRequest();
			requestRepository.save(entry);
			logger.error("Ending request: "+e.getMessage());
			
			throw e;
		}
		
		//no error check
		streamService.streamDailyOnKafka(response);
		
		entry.setRequestState(RequestState.COMPLETED);
		entry.endingRequest();
		requestRepository.save(entry);
		logger.info("Ending request: successful");
		
		return "Successful";
	}
	
	
}
