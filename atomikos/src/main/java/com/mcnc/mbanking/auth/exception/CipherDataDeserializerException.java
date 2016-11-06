package com.mcnc.mbanking.auth.exception;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exception invoked when the {@link ObjectMapper} cannot deserialize JSON String
 * @author sayseakleng
 *
 */
public class CipherDataDeserializerException extends IOException {
	private static final long serialVersionUID = -5753874839617628417L;
	
	public CipherDataDeserializerException(String fieldName) {
		super(String.format("Cannot deserialize and decrypt cipher data for field: %s", fieldName));
	}

}
