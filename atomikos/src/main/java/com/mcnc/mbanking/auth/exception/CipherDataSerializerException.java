package com.mcnc.mbanking.auth.exception;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exception invoked when the {@link ObjectMapper} cannot serialize to JSON String
 * @author sayseakleng
 *
 */
public class CipherDataSerializerException extends IOException {
	
	private static final long serialVersionUID = 575640641979929406L;

	public CipherDataSerializerException(String fieldName) {
		super(String.format("Cannot serialize and encrypt data for field: %s", fieldName));
	}
	
}
