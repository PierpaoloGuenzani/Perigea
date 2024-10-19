package it.perigea.marketdataimporter.exception;

import java.time.LocalDate;

/**
 * An exception that is thrown when date is incorrect
 */
public class InvalideDateException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private LocalDate date;
	
	public InvalideDateException(String msg)
	{
		super(msg);
	}
	
	public InvalideDateException(String message, LocalDate date)
	{
		super(message);
		this.date = date;
	}
	
	public LocalDate getDate()
	{
		return date;
	}

	public InvalideDateException() {
		super();
	}

	public InvalideDateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalideDateException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InvalideDateException(Throwable cause) {
		super(cause);
	}
	
	
}
