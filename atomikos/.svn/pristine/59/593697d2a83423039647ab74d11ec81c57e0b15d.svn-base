package com.mcnc.mbanking.auth.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder to construct {@link ResponseMessage}
 * 
 * @author sayseakleng
 *
 */
public class ResponseMessageBuilder {
	
	private ResponseMessage<Object> message = new ResponseMessage<Object>();
	
	private ResponseMessageBuilder() {
		
	}
	
	/**
	 * Create an instance of ResponseMessageBuilder
	 * 
	 * <br/>
	 * @param isSuccess 
	 * 		<br/>true : create default success Response
	 * 		<br/>else : create default error Response
	 * 		
	 * @return
	 */
	public static ResponseMessageBuilder instance(boolean isSuccess) {
		if(isSuccess) {
			return success();
		}
		else {
			return fail();
		}
	}
	
	
	/**
	 * Create an instance of ResponseMessageBuilder
	 * 
	 * <br/>
	 * @param data 
	 * 		<br/>NULL : create default error Response
	 * 		<br/>Otherwise : create success Response with passing data
	 * 		
	 * @return
	 */
	public static ResponseMessageBuilder instance(Object data) {
		if(data != null) {
			ResponseMessageBuilder success = success();
			success.addData(data);
			return success;
		}
		else {
			return fail();
		}
	}
	
	
	/**
	 * Create default success ResponseMessage
	 * <br/>
	 * ResultCode: 0000
	 * ResultMessage: success
	 * @return
	 */
	public static ResponseMessageBuilder success() {
		ResponseMessageBuilder builder = new ResponseMessageBuilder();
		builder.message.setStatus(ResponseStatus.SUCCESS);
		return builder;
	}
	
	/**
	 * Create default fail ResponseMessage
	 * <br/>
	 * ResultCode: SYS001
	 * ResultMessage: System Error
	 * @return
	 */
	public static ResponseMessageBuilder fail() {
		ResponseMessageBuilder builder = new ResponseMessageBuilder();
		builder.message.setStatus(ResponseStatus.FAIL);
		
		return builder;
	}
	
	
	
	/**
	 * Add Error Code
	 * @param code
	 * @return
	 */
	public ResponseMessageBuilder addCode(ResponseCode code) {
		if(code != null) {
			List<ResponseError> resultCodes = createOrGetResultCodes();
			resultCodes.add(new ResponseError(code));
		}
		return this;
	}
	
	
	/**
	 * Internally create or get ResultCodes from ResponseMessage
	 * @return
	 */
	private List<ResponseError> createOrGetResultCodes() {
		List<ResponseError> resultCodes = this.message.getErrors();
		
		if(resultCodes == null) {
			resultCodes = new ArrayList<>();
			this.message.setErrors(resultCodes);
		}
		
		return resultCodes;
	}
	
	/**
	 * Add Response Data
	 * @param data
	 * @return
	 */
	public ResponseMessageBuilder addData(Object data) {
		this.message.setData(data);
		return this;
	}
	
	
	/**
	 * Finally call to construct ResponseMessage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> ResponseMessage<T> build() {
		
		// check if resultCodes is empty
		List<ResponseError> resultCodes = createOrGetResultCodes();
		if(resultCodes.isEmpty()) {
			if(this.message.getStatus() != ResponseStatus.SUCCESS) {
				addCode(ResponseCode.E0001);
			}
		}
		
		return (ResponseMessage<T>) message;
	}

}
