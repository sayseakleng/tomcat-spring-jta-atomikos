package com.mcnc.commons.dbcp.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encryptor {
	
	public static void main(String[] args) throws InvalidKeyException, IllegalBlockSizeException, 
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

		CipherProvider instance = CipherProvider.instance();

		String plain = args[0];
		System.out.println(instance.encrypt(plain));
	}
}
