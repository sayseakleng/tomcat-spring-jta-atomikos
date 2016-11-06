package com.mcnc.mbanking.auth.service;

import com.mcnc.mbanking.auth.domain.AccountOTP;

public interface AccountOTPService {
	
	int save(AccountOTP otp);
	
	AccountOTP getAccountOTP(long optId);
	
	AccountOTP getTopAccountOTPByCode(String opt);
	
	AccountOTP getTopAccountOTPByAccountNumber(String accountNumber);
	
	int invalidateOPTByCode(String opt);
}
