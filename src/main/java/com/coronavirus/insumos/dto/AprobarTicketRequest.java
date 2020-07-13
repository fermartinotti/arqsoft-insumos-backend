package com.coronavirus.insumos.dto;

public class AprobarTicketRequest {

	Long ticketId;
	Long proveedorId;
	
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public Long getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(Long proveedorId) {
		this.proveedorId = proveedorId;
	}	
}
