package com.mcnc.mbanking.auth.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;

public class TestAESEncryptionUtils {

	private String secretKey = "abc";
	
	@Test
	public void test() throws Exception {
		
		String original = "hello!";
		
		AESKeyAuthentication aesKey = new AESKeyAuthentication(secretKey);
		String encrypted = AESEncryptionUtils.encrypt(original, aesKey);
		
		assertNotNull(encrypted);
		System.out.printf("After encrypted: %s\n", encrypted);
		
		
		String decrypt = AESEncryptionUtils.decrypt(encrypted, aesKey);
		assertEquals(original, decrypt);
		
		System.out.printf("Original encrypted: %s\n", decrypt);
	}

}
