package com.mcnc.mbanking.auth.service;

public interface OTPService {
	
	String generateOTPForAccountNumber(String accountNumber, String password);
	
	boolean validateOTPForAccountNumber(String accountNumber, String otp);
	
}
