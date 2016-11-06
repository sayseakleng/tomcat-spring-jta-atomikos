package com.mcnc.mbanking.auth.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class TestAES256Encryption {
	
	private static Encoder encoder = Base64.getEncoder();
	private static Decoder deEncoder = Base64.getDecoder();
	
	
	private static final String password = "test";
    private static String salt;
    private static int pswdIterations = 65536  ;
    private static int keySize = 256;
    private byte[] ivBytes;
 
    public String encrypt(String plainText) throws Exception {   
         
        //get salt
        salt = generateSalt();      
        byte[] saltBytes = salt.getBytes("UTF-8");
         
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), 
                saltBytes, 
                pswdIterations, 
                keySize
                );
 
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
 
        //encrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return encoder.encodeToString(encryptedTextBytes);
    }
 
    public String decrypt(String encryptedText) throws Exception {
 
        byte[] saltBytes = salt.getBytes("UTF-8");
        byte[] encryptedTextBytes = deEncoder.decode(encryptedText);
 
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), 
                saltBytes, 
                pswdIterations, 
                keySize
                );
 
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
 
        // Decrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
     
 
        byte[] decryptedTextBytes = null;
        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
 
        return new String(decryptedTextBytes);
    }
 
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String s = new String(bytes);
        return s;
    }

	@Test
	public void test() throws Exception {
		
		System.out.println("Encrypted string:" + this.encrypt("Hello"));           
        String encryptedText = this.encrypt("Hello");
        System.out.println("Decrypted string:" + this.decrypt(encryptedText));
	}
	
	

	@Test
	public void test2() throws Exception {
		
		String originalText = "Hello from me";
		
		try{
			byte[] plainBytes = originalText.getBytes();
			
			//Generate the key first
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			
			// but need to update jre\lib\security default lib doesn't support more than 128
			keyGen.init(256); // Key size Wrong keysize: must be equal to 128, 192 or 256
			
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
	
	
	@Test
	public void testEncodeBytesToKey() throws NoSuchAlgorithmException, NoSuchPaddingException, 
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); // Key size
		SecretKey key = keyGen.generateKey();
		byte[] encoded = key.getEncoded();
		
		key = new SecretKeySpec(encoded, "AES");
		
		
		String originalText = "Hello from me";
		
		// Create Cipher instance and initialize it to encrytion mode
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Transformation of the algorithm
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherBytes = cipher.doFinal(originalText.getBytes());
		
		String encodeToString = encoder.encodeToString(cipherBytes);
		System.out.printf("Encryted text: %s\n", encodeToString);

		// Reinitialize the Cipher to decryption mode
		cipher.init(Cipher.DECRYPT_MODE,key, cipher.getParameters());
		byte[] plainBytesDecrypted = cipher.doFinal(deEncoder.decode(encodeToString));

		System.out.printf("Decrupted text: %s\n", new String(plainBytesDecrypted));
		
	}
	
}