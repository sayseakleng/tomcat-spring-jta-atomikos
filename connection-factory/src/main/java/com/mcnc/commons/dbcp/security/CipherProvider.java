package com.mcnc.commons.dbcp.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CipherProvider {
	private static final Logger logger = LoggerFactory.getLogger(CipherProvider.class);
	
	public static final String DEFAULT_PASSWORD 		= "com.mcnc.commons.security.secret";
	public static final String DEFAULT_SALT	 			= "com.mcnc.commons.security.salt";
	public static final int DEFAULT_ITERATIONS_COUNT	= 64000;
	public static final int DEFAULT_AES_KEY_LENGTH	 	= 128;
	
	private static CipherProvider provider;
	
	private Decoder decoder = Base64.getDecoder();
	private Encoder encoder = Base64.getEncoder();
	private SecretKeySpec secretKeySpec;
	
	private String password = DEFAULT_PASSWORD;
    private String salt = DEFAULT_SALT;
    private int iterationsEncrypt = DEFAULT_ITERATIONS_COUNT;
    private int aesKeyLength = DEFAULT_AES_KEY_LENGTH;
	
	static {
		try {
			provider = new CipherProvider();
		} catch (NoSuchAlgorithmException e) {
			logger.error("Cannot initiate CipherProvider", e);
		} catch (InvalidKeySpecException e) {
			logger.error("Cannot initiate CipherProvider", e);
		}
	}
	
	
	public static CipherProvider instance() {
		return provider;
	}
	
	private CipherProvider() throws NoSuchAlgorithmException, InvalidKeySpecException {
		
	    byte[] saltBytes = salt.getBytes();
	    
	    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");  //PBKDF2WithHmacSHA1
	    
	    SecretKey secretKey = secretKeyFactory.generateSecret(
	    		new PBEKeySpec(password.toCharArray(), saltBytes, iterationsEncrypt, aesKeyLength));
	    
	    secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
	}
	
	
	public String encrypt(String plain) throws IllegalBlockSizeException, BadPaddingException, 
		InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		
		Cipher aesCipherEncrypt = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    aesCipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	    byte[] doFinal = aesCipherEncrypt.doFinal(plain.getBytes(StandardCharsets.UTF_8));
	    
	    return encoder.encodeToString(doFinal);
	}
	
	
	public String decrypt(String encrypted) throws IllegalBlockSizeException, BadPaddingException, 
		InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		
		Cipher aesCipherEncrypt = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    aesCipherEncrypt.init(Cipher.DECRYPT_MODE, secretKeySpec);
	    byte[] doFinal = aesCipherEncrypt.doFinal(decoder.decode(encrypted));
	    
	    return new String(doFinal, StandardCharsets.UTF_8);
	}
	
}
