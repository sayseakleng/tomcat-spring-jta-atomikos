package com.mcnc.mbanking.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcnc.mbanking.auth.dao.AccountOTPDAO;
import com.mcnc.mbanking.auth.domain.AccountOTP;
import com.mcnc.mbanking.auth.service.AccountOTPService;

@Service
@Transactional
public class AccountOTPServiceImpl implements AccountOTPService {
	@Autowired
	private AccountOTPDAO accountOTPDAO;

	@Override
	public int save(AccountOTP otp) {
		return accountOTPDAO.save(otp);
	}
	
	@Override
	public AccountOTP getAccountOTP(long optId) {
		return accountOTPDAO.getAccountOTP(optId);
	}

	@Override
	public AccountOTP getTopAccountOTPByCode(String opt) {
		return accountOTPDAO.getTopAccountOTPByCode(opt);
	}

	@Override
	public int invalidateOPTByCode(String opt) {
		return accountOTPDAO.invalidateOPTByCode(opt);
	}

	@Override
	public AccountOTP getTopAccountOTPByAccountNumber(String accountNumber) {
		return accountOTPDAO.getTopAccountOTPByAccountNumber(accountNumber);
	}

}
