package com.mcnc.mbanking.auth.exception;

import java.io.IOException;

/**
 * Exception invoked when cannot find AESKeyAuthentication from session
 * @author sayseakleng
 *
 */
public class InvalidAESKeyAuthenticationException extends IOException {

	private static final long serialVersionUID = -6877057574568920026L;
	
	public InvalidAESKeyAuthenticationException() {
		super("Unavailable or invalid AES key from session");
	}

}
