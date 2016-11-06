package com.mcnc.mbanking.auth.dto;

/**
 * Represents Response Error
 * @author sayseakleng
 *
 */
public class ResponseError {
	private ResponseCode code;
	private String message;
	
	public ResponseError() {
	}
	
	public ResponseError(ResponseCode code) {
		this.code = code;
		this.message = code.getMessage();
	}
	
	public ResponseError(ResponseCode code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ResponseCode getCode() {
		return code;
	}
	public void setCode(ResponseCode code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
