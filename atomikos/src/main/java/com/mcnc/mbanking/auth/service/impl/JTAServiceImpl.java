package com.mcnc.mbanking.auth.service.impl;

import java.util.Date;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcnc.mbanking.auth.domain.AccountOTP;
import com.mcnc.mbanking.auth.domain.User;
import com.mcnc.mbanking.auth.repository.UserRepository;
import com.mcnc.mbanking.auth.service.AccountOTPService;
import com.mcnc.mbanking.auth.service.JTAService;

@Service
@Transactional(rollbackFor = Exception.class)
public class JTAServiceImpl implements JTAService {
	
	@Autowired
	private AccountOTPService accountOTPService;
	
	@Autowired
	private UserRepository repo;
	
//	@Autowired
//	private PlatformTransactionManager manager;

	@Override
	public boolean work(int i) throws Exception {
		// manually open transaction
//		TransactionStatus transaction = manager.getTransaction(new DefaultTransactionDefinition());
		
		Totp totp = new Totp(Base32.random());
		
		AccountOTP otp = new AccountOTP();
		otp.setAccountId(1);
		otp.setIssuedDateTime(new Date());
		otp.setOtp(totp.now());
		otp.setAccountNumber(totp.now());
		otp.setValid(Boolean.TRUE);
		
		accountOTPService.save(otp);
		
		if(i == 1) {
			throw new Exception(String.format("Hi %d", i));
//			manager.rollback(transaction);
		}
		
		
		User user = new User();
		user.setUserName("abc");
		user.setPassword("abc");
		repo.save(user);
		
		if(i == 2) {
			throw new Exception(String.format("Hi %d", i));
//			manager.rollback(transaction);
		}
		
		if(i != 1 && i != 2) {
//			manager.commit(transaction);
		}
		
		return true;
	}

}
