package com.mcnc.mbanking.auth.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcnc.mbanking.auth.domain.RSAPairKeyAuthentication;
import com.mcnc.mbanking.auth.spring.context.SpringContextHolder;

public class RSAPairKeyAuthenticationUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(RSAPairKeyAuthenticationUtils.class);
	
	protected static final Integer DEFAULT_KEY_LENGTH 			= 2048;
	protected static final String DEFAULT_KEY_ALGORITHM 		= "RSA";
	
	
	public static RSAPairKeyAuthentication generatePairKeyAuthentication() {
		
		RSAPairKeyAuthentication pairKeyAuthentication = null;
		
		try {
			Integer keyLength = SpringContextHolder.getProperty(PropertyKeys.RSA_LENGTH, Integer.class);
			String algorithm = DEFAULT_KEY_ALGORITHM;
			
			if(keyLength == null) {
				keyLength = DEFAULT_KEY_LENGTH;
			}
			
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
			kpg.initialize(keyLength);
			KeyPair kp = kpg.genKeyPair();
			
			pairKeyAuthentication = new RSAPairKeyAuthentication();
			pairKeyAuthentication.setPrivateKey(kp.getPrivate());
			pairKeyAuthentication.setPublicKey(kp.getPublic());

		} catch (NoSuchAlgorithmException e) {
			logger.error("Cannot generate KeyPair", e);
		}
		
		return pairKeyAuthentication;
	}
}
