package it.perigea.marketdataimporter.logger.postgre.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.annotation.PostConstruct;
import javax.persistence.Column;

/**
 * A class to store the request type and state
 */
@Entity
@Table(name = "request")
public class Request
{
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="REQUEST_TYPE")
	@Enumerated(EnumType.STRING)
	private RequestType requestType;
	
	@Column(name="REQUEST_STATE")
	@Enumerated(EnumType.STRING)
	private RequestState requestState;
	
	@Column(name="STARTING_TIME", columnDefinition="TIMESTAMP")
	private LocalDateTime startingTime;
	
	@Column(name="ENDING_TIME", columnDefinition="TIMESTAMP")
	private LocalDateTime endingTime;
	
	public Request()
	{
		this.startingTime = LocalDateTime.now();
	}
	
	public void endingRequest()
	{
		this.endingTime = LocalDateTime.now();
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public RequestType getRequestType()
	{
		return requestType;
	}

	public void setRequestType(RequestType requestType)
	{
		this.requestType = requestType;
	}

	public RequestState getRequestState()
	{
		return requestState;
	}

	public void setRequestState(RequestState requestState)
	{
		this.requestState = requestState;
	}

	public LocalDateTime getStartingTime()
	{
		return startingTime;
	}

	public void setStartingTime(LocalDateTime startingTime)
	{
		this.startingTime = startingTime;
	}

	public LocalDateTime getEndingTime()
	{
		return endingTime;
	}

	public void setEndingTime(LocalDateTime endingTime)
	{
		this.endingTime = endingTime;
	}
}
