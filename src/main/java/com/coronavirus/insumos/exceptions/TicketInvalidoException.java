package com.coronavirus.insumos.exceptions;

public class TicketInvalidoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7210501555635068895L;
	
	public TicketInvalidoException(String message) {
		super(message);
	}

}
