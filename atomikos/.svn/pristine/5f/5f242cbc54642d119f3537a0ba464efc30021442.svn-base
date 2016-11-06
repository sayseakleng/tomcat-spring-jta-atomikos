package com.mcnc.mbanking.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;

public abstract class AESEncryptionUtils {
	private static final Logger logger = LoggerFactory.getLogger(AESEncryptionUtils.class);

	public static final String DEFAULT_TRANSFORMATION 	= "AES/CBC/PKCS5Padding";
	
	private static Encoder encoder = Base64.getEncoder();
	private static Decoder deEncoder = Base64.getDecoder();
	private static AES256JNCryptor cryptor = new AES256JNCryptor();
	
	/**
	 * Encrypt text using {@link AESKeyAuthentication}
	 * @param original
	 * @param aesKey
	 * @return Encrypted text
	 */
	public static String encrypt(String original, AESKeyAuthentication aesKey) {
		Assert.notNull(original, "original text cannot be null");
		
		byte[] encrypt = encrypt(original.getBytes(StandardCharsets.UTF_8), aesKey);
		
		if(encrypt != null) {
			return encoder.encodeToString(encrypt);
		}
		else {
			return null;
		}
		
	}
	
	
	/**
	 * Encrypt byte array using {@link AESKeyAuthentication}
	 * @param original
	 * @param aesKey
	 * @return Encrypted byte array
	 */
	public static byte[] encrypt(byte[] original, AESKeyAuthentication aesKey) {
		byte[] encrypted = null;

		try {
			encrypted = cryptor.encryptData(original, aesKey.getSecretKey().toCharArray());

		} catch (Exception e) {
			logger.error("Cannot encrype data", e);
		}

		return encrypted;
	}
	
	
	/**
	 * Decrypt text using {@link AESKeyAuthentication}
	 * @param encrypted
	 * @param aesKey {@link AESKeyAuthentication}
	 * @return plain text
	 */
	public static String decrypt(String encrypted, AESKeyAuthentication aesKey) {
		Assert.notNull(encrypted, "original text cannot be null");

		String result = null;
		
		try {
			byte[] decrypt = decrypt(deEncoder.decode(encrypted), aesKey);
			if(decrypt != null) {
				result = new String(decrypt, StandardCharsets.UTF_8);
			}
		} catch (Exception e) {
			logger.error("Base64 cannot decode data", e);
		}
		
		return result;
	}

	/**
	 * Decrypt byte array using {@link AESKeyAuthentication}
	 * @param encrypted
	 * @param aesKey {@link AESKeyAuthentication}
	 * @return plain byte array
	 */
	public static byte[] decrypt(byte[] encrypted, AESKeyAuthentication aesKey) {
		byte[] decrypted = null;
		
		try {
			decrypted = cryptor.decryptData(encrypted, aesKey.getSecretKey().toCharArray());
		} catch (Exception e) {
			logger.error("Cannot decrypt data", e);
		}
		
		return decrypted;
	}
}
