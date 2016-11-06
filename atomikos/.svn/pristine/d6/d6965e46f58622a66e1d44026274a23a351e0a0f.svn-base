package com.mcnc.mbanking.auth.util;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.jboss.aerogear.security.otp.api.Clock;
import org.junit.Test;

public class TestOTP {
	
	private String sharedSecret = Base32.encode("abc".getBytes());;

	@Test
	public void test() throws InterruptedException {
		
		Clock clock = new Clock(30); // seconds
		
		// generate 		
		
		Totp totp = new Totp(sharedSecret, clock);
		String otp = totp.now();
		System.out.println(otp);
		
		
		
		// verify
		
		totp = new Totp(sharedSecret, clock);
		boolean verify = totp.verify(otp);
		System.out.println(verify);
		
		Thread.sleep(40_000);
		
		// 1 -> 999 (1000)
		
		//5 -> 6000 (7000)
		
		// 10 - > (12000) 13_000
		
		// 30 - > (49_000) 50_000
		
		//totp = new Totp(sharedSecret);
		verify = totp.verify(otp);
		System.out.println(verify);
		
	}

}
