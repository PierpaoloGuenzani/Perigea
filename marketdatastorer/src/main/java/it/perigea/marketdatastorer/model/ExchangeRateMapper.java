package it.perigea.marketdatastorer.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.perigea.marketdata.common.ExchangeRateDTO;

@Mapper(componentModel = "spring", imports = {LocalDate.class, Instant.class, ZoneId.class} )
public interface ExchangeRateMapper
{
	@Mapping(target = "date", expression = 
			"java(LocalDate.ofInstant(Instant.ofEpochMilli(source.getTimeStamp()), ZoneId.of(\"UTC\")))")
	ExchangeRate mapToEntity (ExchangeRateDTO source);

	List<ExchangeRate> mapToDto (List<ExchangeRateDTO> source);
}
