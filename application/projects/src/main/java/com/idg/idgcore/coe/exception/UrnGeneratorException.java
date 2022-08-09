package com.idg.idgcore.coe.exception;

public class UrnGeneratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UrnGeneratorException(String message) {
		super(message);
	}

	public UrnGeneratorException(String message, Throwable e) {
		super(message, e);
	}

}
