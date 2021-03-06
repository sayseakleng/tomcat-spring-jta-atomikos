package com.mcnc.mbanking.auth.dto;

import java.util.List;

/**
 * Represent Response Message format
 * @author sayseakleng
 *
 * @param <T>
 */
public class ResponseMessage <T> {
	
	private ResponseStatus status;
	private List<ResponseError> errors;
	private T data;
	public ResponseStatus getStatus() {
		return status;
	}
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	public List<ResponseError> getErrors() {
		return errors;
	}
	public void setErrors(List<ResponseError> errors) {
		this.errors = errors;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
