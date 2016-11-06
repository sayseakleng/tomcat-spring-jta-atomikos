package com.mcnc.mbanking.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class PasswordEncoder {
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static String encode(String password) {
		return encoder.encode(password);
	}
}
