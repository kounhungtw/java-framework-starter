package com.system.exception;

import javax.ws.rs.WebApplicationException;

public class WebApplicationErrorMessage {
	
	private int status;
	
	private String message;
	
	private String detailMessage;
	
	private String path;
	
	public WebApplicationErrorMessage(WebApplicationException ex, String path) {
		this.status = ex.getResponse().getStatus();
		this.message = ex.getMessage();
		this.detailMessage = ex.getCause() != null ? ex.getCause().getMessage() : "";
		this.path = path;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
