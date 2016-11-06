package com.mcnc.mbanking.auth.dao;

import org.springframework.stereotype.Repository;

import com.mcnc.mbanking.auth.domain.AccountOTP;

@Repository
public interface AccountOTPDAO {
	
	int save(AccountOTP otp);
	
	AccountOTP getAccountOTP(long optId);
	
	AccountOTP getTopAccountOTPByCode(String otp);
	
	AccountOTP getTopAccountOTPByAccountNumber(String accountNumber);
	
	int invalidateOPTByCode(String otp);
	
}
