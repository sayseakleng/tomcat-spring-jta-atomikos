package com.mcnc.commons.dbcp.security;

import static org.junit.Assert.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

public class TestCipherProvider {
	String plain = "Hello";

	@Test
	public void testEncrypt() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		
		CipherProvider instance = CipherProvider.instance();
		String encrypt = instance.encrypt(plain);
		assertNotNull(encrypt);
		System.out.println(encrypt);
	}
	
	@Test
	public void  testDecrypt() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		CipherProvider instance = CipherProvider.instance();
		String decrypt = instance.decrypt("25L4nyt8Z+2+jX/tAwbV+A==");
		assertNotNull(decrypt);
		assertEquals(decrypt, plain);
		System.out.println(decrypt);
	}
	
	
	@Test
	public void testUserPassword() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		
		CipherProvider instance = CipherProvider.instance();
		String encrypt = instance.encrypt("sa");
		System.out.println(encrypt);
	
		encrypt = instance.encrypt("G0CamHell0");
		System.out.println(encrypt);
	}

}
