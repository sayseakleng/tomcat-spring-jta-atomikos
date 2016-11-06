package com.mcnc.mbanking.auth.dto;

import com.mcnc.mbanking.auth.domain.Customer;

public class LoginDTO extends Customer {
	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
