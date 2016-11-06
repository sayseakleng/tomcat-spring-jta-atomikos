package com.mcnc.mbanking.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mcnc.mbanking.auth.domain.Account;
import com.mcnc.mbanking.auth.domain.Customer;
import com.mcnc.mbanking.auth.dto.LoginDTO;
import com.mcnc.mbanking.auth.dto.ResponseMessage;
import com.mcnc.mbanking.auth.dto.ResponseMessageBuilder;
import com.mcnc.mbanking.auth.service.AccountService;
import com.mcnc.mbanking.auth.service.CustomerService;
import com.mcnc.mbanking.auth.service.JTAService;
import com.mcnc.mbanking.auth.service.OTPService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JTAService jtaService;
	
	@Autowired
	private OTPService otpService;
	
	@PostMapping("verify")
	public ResponseMessage<Boolean> verifyAccount(@RequestBody Account account) {
		boolean isValid = false;
		
		isValid = accountService.validateAccount(account.getAccountNumber(), account.getPassword());
		
		return ResponseMessageBuilder.instance(isValid)
				.build();
		
	}
	
	
	@PostMapping("/otp")
	public ResponseMessage<Boolean> generateOtp(@RequestBody LoginDTO login) {
		
		boolean isValid = false;
		
		Customer customerByDetail = customerService.getCustomerByDetail(login);
		if(customerByDetail != null) {
			Account accountByAccountNumber = accountService.getAccountByAccountNumber(login.getAccountNumber());
			otpService.generateOTPForAccountNumber(login.getAccountNumber(), accountByAccountNumber.getPassword());
			isValid = true;
		}
		
		
		return ResponseMessageBuilder.instance(isValid)
				.build();
	}
	
	
	@GetMapping("/jta")
	public boolean testJta(@RequestParam int i) throws Exception {
		return jtaService.work(i);
		
	}
	

}
