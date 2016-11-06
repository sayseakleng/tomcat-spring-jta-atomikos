package com.mcnc.mbanking.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcnc.mbanking.auth.dao.AccountDAO;
import com.mcnc.mbanking.auth.domain.Account;
import com.mcnc.mbanking.auth.service.AccountService;
import com.mcnc.mbanking.auth.util.PasswordEncoder;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public Account getAccountByAccountNumber(String accountNumber) {
		return accountDAO.getAccountByAccountNumber(accountNumber);
	}

	@Override
	public boolean validateAccount(String accountNumber, String password) {
		boolean isValid = false;
		
		Account accountByAccountNumber = accountDAO.getAccountByAccountNumber(accountNumber);
		if(accountByAccountNumber != null) {
			String encodePassword = PasswordEncoder.encode(password);
			isValid = encodePassword.equals(accountByAccountNumber.getPassword());
		}
		
		return isValid;
	}

}
