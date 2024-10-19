package it.perigea.marketdataanalyzer.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.perigea.marketdata.common.ExchangeRate;

@Service
public class AnalyzerService {
	
	@Autowired
	private DataImporterService importer;

	
	public List<ExchangeRateEstimation> exchangeRateEstimation(String symbol)
	{
		int numberOfBootstrapSequence = 10000;
		int decimalNumber = 100;
		
		List<ExchangeRateEstimation> list = new LinkedList<>();
		
		ExchangeRate[] array = importer.importExchangeRate(symbol);
		
		int[] stima = { 1, 10, 30, 60, 90, 365, 730, 1095, 3650, 7300, 10950};
		
		Double[] bootstrapMean = new Double[numberOfBootstrapSequence];
		Double[] bootstrapVar = new Double[numberOfBootstrapSequence];
		
	//X = tempo (variabile indipendente), Y = valore (variabile dipendente)
		
		/*double totalY = double.ZERO;
		double squareTotalY = double.ZERO;
		
		long totalX = 0;
		long squareTotalX = 0;
		
		double totalXY = double.ZERO;
		double arrayLength = new double(array.length);
		for(int i=0; i < array.length; i++)
		{
			double val = new double(array[i].getWeightedAverage());
			totalY = totalY.add(val);
			squareTotalY = squareTotalY.add(val.pow(2));
			
			long day = array[i].getDate().toEpochDay();
			totalX += day;
			squareTotalX += day*day;
			
			totalXY = totalXY.add(val.multiply(new double(day)));
		}
		double averageY = totalY.divide(arrayLength, decimalNumber, double.ROUND_UP);
		//double varY = (squareTotalY.divide(arrayLength)).subtract(averageY.pow(2));
		
		double averageX = new double(totalX).divide(arrayLength, decimalNumber, double.ROUND_UP);
		double varX = (new double(squareTotalX).divide(arrayLength, decimalNumber, double.ROUND_UP)).subtract(averageX.pow(2));
		
		// cov = (media x*y) - (media x * media y)
		double cov = (totalXY.divide(arrayLength, decimalNumber, double.ROUND_UP)).subtract(averageY.multiply(varX));
		
		// y = mx + q;	m = cov / varX; q = mediaY - m*mediaX
		
		double m = cov.divide(varX, decimalNumber, double.ROUND_UP);
		double q = averageY.subtract( m.multiply(averageX));
		
		int[] stima = { 1, 10, 30, 60, 90, 365, 730, 1095, 3650, 7300, 10950};
		LocalDate nowDate = LocalDate.now();
		
		for(int s = 0; s < stima.length; s++)
		{
			LocalDate date =  LocalDate.ofEpochDay(nowDate.toEpochDay()+stima[s]);
			Double value = m.multiply(new double(date.toEpochDay())).add(q).doubleValue();
			
			list.add(new ExchangeRateEstimation(symbol, value, date));
		}*/
		
		//senza formule di campionamento
		//X = tempo (variabile indipendente), Y = valore (variabile dipendente)
		{
		double totalY = 0;
		double squareTotalY = 0;
		
		long totalX = 0;
		long squareTotalX = 0;
		
		double totalXY = 0;
		for(int i=0; i < array.length; i++)
		{
			double val = array[i].getWeightedAverage();
			totalY += val; 
			squareTotalY += val*val;
			
			long day = array[i].getDate().toEpochDay();
			totalX += day;
			squareTotalX += day*day;
			
			totalXY += val*day;
		}
		double averageY = totalY / array.length;
		double varY = (squareTotalY / array.length) - (averageY * averageY);
		
		double averageX = totalX / array.length;
		double varX = (squareTotalX / array.length) - (averageX*averageX);
		
		// cov = (media x*y) - (media x * media y)
		double cov = (totalXY / array.length) - (averageX * averageY);
		
		// y = mx + q;	m = cov / varX; q = mediaY - m*mediaX
		
		double m = cov / varX;
		double q = averageY - m * averageX;
		
		LocalDate nowDate = LocalDate.now();
		
		for(int s = 0; s < stima.length; s++)
		{
			LocalDate date =  LocalDate.ofEpochDay(nowDate.toEpochDay()+stima[s]);
			Double value = (m * date.toEpochDay()) + q;
			
			list.add(new ExchangeRateEstimation(symbol, value, date));
		}}
		
		{
		int dim = array.length - 1;
		double totalY = 0;
		double squareTotalY = 0;
		
		long totalX = 0;
		long squareTotalX = 0;
		
		double totalXY = 0;
		for(int i=0; i < array.length; i++)
		{
			double val = array[i].getWeightedAverage();
			totalY += val; 
			squareTotalY += val*val;
			
			long day = array[i].getDate().toEpochDay();
			totalX += day;
			squareTotalX += day*day;
			
			totalXY += val*day;
		}
		double averageY = totalY / array.length;
		double varY = (squareTotalY / dim) - (averageY * averageY);
		
		double averageX = totalX / array.length;
		double varX = (squareTotalX / dim) - (averageX*averageX);
		
		// cov = (media x*y) - (media x * media y)
		double cov = (totalXY / dim) - ((totalX / dim) * averageY);
		
		// y = mx + q;	m = cov / varX; q = mediaY - m*mediaX
		
		double m = cov / varX;
		double q = averageY - m * averageX;
		
		LocalDate nowDate = LocalDate.now();
		
		for(int s = 0; s < stima.length; s++)
		{
			LocalDate date =  LocalDate.ofEpochDay(nowDate.toEpochDay()+stima[s]);
			Double value = (m * date.toEpochDay()) + q;
			
			list.add(new ExchangeRateEstimation(symbol, value, date));
		}}
		
		//BOOTSTRAP
		/*
		Random random = new Random();
		double averageOfMean = 0;
		for(int j=0; j < numberOfBootstrapSequence; j++)
		{
			total = 0;
			squareTotal = 0;
			for(int i=0; i < array.length; i++)
			{
				int index = random.nextInt(array.length);
				
				total += array[index].getWeightedAverage();
				squareTotal += array[index].getWeightedAverage()*array[index].getWeightedAverage();
			}
			bootstrapMean[j] = total / array.length;
			bootstrapVar[j] = (squareTotal / array.length) - (bootstrapMean[j]*bootstrapMean[j]);
			averageOfMean += bootstrapMean[j];
		}
		System.out.println(averageOfMean/numberOfBootstrapSequence);*/
		
		return list;
	}
}
