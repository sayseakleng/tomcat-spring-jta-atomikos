package com.mcnc.mbanking.auth.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;
import com.mcnc.mbanking.auth.domain.RSAPairKeyAuthentication;
import com.mcnc.mbanking.auth.dto.AESKeyExchangeDTO;
import com.mcnc.mbanking.auth.dto.OTPRequestDTO;
import com.mcnc.mbanking.auth.dto.OTPValidationDTO;
import com.mcnc.mbanking.auth.dto.ResponseCode;
import com.mcnc.mbanking.auth.dto.ResponseMessage;
import com.mcnc.mbanking.auth.dto.ResponseMessageBuilder;
import com.mcnc.mbanking.auth.service.OTPService;
import com.mcnc.mbanking.auth.util.Consts;
import com.mcnc.mbanking.auth.util.RSAEncryptionUtils;
import com.mcnc.mbanking.auth.util.RSAPairKeyAuthenticationUtils;
import com.mcnc.mbanking.auth.validator.AESKeyExchangeValidator;

@RestController
public class AuthController implements WebBindingInitializer {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AESKeyExchangeValidator keyExchangeValidator;
	
	@Autowired
	private OTPService otpService;
	
	
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.addValidators(keyExchangeValidator);
	}

	
	/**
	 * Generate RSA Private/Public key pair and save to session
	 * 
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/api/secure/rsa")
	public ResponseEntity<byte[]> generateKeyPairAuthentication(HttpSession httpSession) {
		
		RSAPairKeyAuthentication pairKeyAuthentication = RSAPairKeyAuthenticationUtils
				.generatePairKeyAuthentication();
		
		if(pairKeyAuthentication != null) {
			httpSession.setAttribute(Consts.RSA_SESSION_KEY, pairKeyAuthentication);
			return new ResponseEntity<byte[]>(pairKeyAuthentication.getPublicKey().getEncoded(), HttpStatus.OK);
		}
		else {
			logger.error("Cannot create RSA PairKey");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

	/**
	 * save AES key from client to session
	 * 
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/api/secure/aes")
	public ResponseMessage<Boolean> saveAES(HttpSession httpSession, 
			@Validated @RequestBody AESKeyExchangeDTO keyExchange) {
		
		boolean isSuccess = false;
		
		
		Object rsaAuth = httpSession.getAttribute(Consts.RSA_SESSION_KEY);
		
		if(rsaAuth != null && rsaAuth instanceof RSAPairKeyAuthentication) {
			
			RSAPairKeyAuthentication pairKeyAuthentication = (RSAPairKeyAuthentication) rsaAuth;
				
				String key = RSAEncryptionUtils.decrypt(keyExchange.getEncode(), 
						pairKeyAuthentication.getPrivateKey());
				
				if(!StringUtils.isEmpty(key)) {
					httpSession.setAttribute(Consts.AES_SESSION_KEY, new AESKeyAuthentication(key));
					isSuccess = true;
				}
			
		}
		
		
		
		if(isSuccess) {
			return ResponseMessageBuilder.success()
					.build();
		}
		else {
			logger.error("There is no RSA key in session");
			return ResponseMessageBuilder.fail()
					.addCode(ResponseCode.E0002)
					.build();
		}
		
		
	}
	
	
	/**
	 * To clear Private/Public Keys from session
	 * 
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/api/auth/logout")
	public ResponseMessage<Boolean> logout(HttpSession httpSession) {
		
		httpSession.invalidate();
		
		return ResponseMessageBuilder.instance(Boolean.TRUE)
				.build();
	
	}
	
	
	@PostMapping("/api/auth/otp")
	public ResponseMessage<Boolean> requestOTP(@RequestBody OTPRequestDTO otpRequest) {
		
		String generateOTPForAccountNumber = otpService.generateOTPForAccountNumber(otpRequest.getAccountNumber(), 
				otpRequest.getPassword());
		
		boolean isResult = generateOTPForAccountNumber != null;
		
		return ResponseMessageBuilder.instance(isResult)
				.build();
	}
	
	
	@PostMapping("/api/auth/session")
	public ResponseMessage<Boolean> requestOTP(@RequestBody OTPValidationDTO otpValidation) {
		
		boolean isValid = otpService.validateOTPForAccountNumber(otpValidation.getAccountNumber(), otpValidation.getOtp());
		
		return ResponseMessageBuilder.instance(isValid)
				.build();
	}
	
	
	
}
