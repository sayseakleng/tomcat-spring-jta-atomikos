package com.mcnc.mbanking.auth.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class TestAES128Encryption {
	
	
	private static Encoder encoder = Base64.getEncoder();
	private static Decoder deEncoder = Base64.getDecoder();
	
	public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return encoder.encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(deEncoder.decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		
		String data = "";
		for(int i = 0; i < 1000; i++) {
			data = data.concat("adgdg");
		}
		
		
		String key = "Bar12345Bar12345"; // 128 bit key
		String initVector = "RandomInitVector"; // 16 bytes IV
		
		String encrypt = encrypt(key, initVector, data);
	     
		System.out.printf("Encrypt: %s\n", encrypt);

		String decrypt = decrypt(key, initVector, encrypt);
	     
		System.out.printf("Original: %s\n", decrypt);
	}
	
	
	
	@Test
	public void test2() throws Exception {
		
		String originalText = "Hello from me";
		
		try{
			byte[] plainBytes = originalText.getBytes();
			
			//Generate the key first
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			//keyGen.init(new SecureRandom());
			keyGen.init(128); // Key size
			SecretKey  key = keyGen.generateKey();
			
			// Create Cipher instance and initialize it to encrytion mode
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Transformation of the algorithm
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherBytes = cipher.doFinal(plainBytes);
			
			String encodeToString = encoder.encodeToString(cipherBytes);
			System.out.printf("Encryted text: %s\n", encodeToString);

			// Reinitialize the Cipher to decryption mode
			cipher.init(Cipher.DECRYPT_MODE,key, cipher.getParameters());
			byte[] plainBytesDecrypted = cipher.doFinal(deEncoder.decode(encodeToString));

			System.out.printf("Decrupted text: %s\n", new String(plainBytesDecrypted));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
	
