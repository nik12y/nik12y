package com.idg.idgcore.coe.exception;

import java.util.Date;

public class UrnGeneratorError {

	private Date timestamp;
	private String message;
	private String details;
	private Integer status;

	public UrnGeneratorError(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public UrnGeneratorError(Date timestamp, String message, String details, Integer status) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
