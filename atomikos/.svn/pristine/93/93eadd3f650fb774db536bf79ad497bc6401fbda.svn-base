package com.mcnc.mbanking.auth.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.PublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcnc.mbanking.auth.controller.AuthController;
import com.mcnc.mbanking.auth.controller.MessageController;
import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;
import com.mcnc.mbanking.auth.domain.RSAPairKeyAuthentication;
import com.mcnc.mbanking.auth.dto.AESKeyExchangeDTO;
import com.mcnc.mbanking.auth.dto.ApiRequestMessage2;
import com.mcnc.mbanking.auth.dto.ApiResponseMessage2;
import com.mcnc.mbanking.auth.dto.ResponseMessage;
import com.mcnc.mbanking.auth.secure.mapper.CipherDataObjectMapperConfigurationAdaptor;

public class TestCipherDataObjectMapperConfigurationAdaptor {
	
	@Mock
	private HttpServletRequest request;
	
	private HttpSession session = new MockHttpSession();
	
	
	@Before
	public void before() {
	    MockitoAnnotations.initMocks(this);
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	public void testWithoutSession() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		CipherDataObjectMapperConfigurationAdaptor adaptor = 
				new CipherDataObjectMapperConfigurationAdaptor();

	
		adaptor.configureObjectMapper(objectMapper);
		
		
		ApiResponseMessage2 msg = new ApiResponseMessage2();
		msg.setEncryptText("abc");
		
		Boolean isError = false;
		try {
			String writeValueAsString = objectMapper.writeValueAsString(msg);
			System.out.println(writeValueAsString);
			
		} catch (JsonProcessingException e) {
			isError = true;
			e.printStackTrace();
		}

		assertTrue(isError);
	}
	
	@Test
	public void testWithIncorrectClientData() throws IOException {
		Mockito.when(request.getSession()).thenReturn(session);
		
		AuthController authController = new AuthController();
		authController.generateKeyPairAuthentication(session);
		
		
		
		// prepare data from client with encrypt data
		ObjectMapper clientOMapper = new ObjectMapper();
		ApiRequestMessage2 req = new ApiRequestMessage2();
		req.setName("Dara");
		String writeValueAsString = clientOMapper.writeValueAsString(req);
		
		
		
		
		// server read data & and make response
		ObjectMapper objectMapper = new ObjectMapper();
		CipherDataObjectMapperConfigurationAdaptor adaptor = 
				new CipherDataObjectMapperConfigurationAdaptor();

		adaptor.configureObjectMapper(objectMapper);
		req = objectMapper.readValue(writeValueAsString, ApiRequestMessage2.class);
		System.out.println(req);
	}
	
	@Test
	public void testSession2() throws IOException {
		
		String aesPass = "aesPass";
		
		Mockito.when(request.getSession()).thenReturn(session);
		
		AuthController authController = new AuthController();
		authController.generateKeyPairAuthentication(session);
		
		
		
		// RSA session
		RSAPairKeyAuthentication rsaPairKey = null;
		Object attribute = session.getAttribute(Consts.RSA_SESSION_KEY); 
		if(attribute instanceof RSAPairKeyAuthentication) {
			rsaPairKey = (RSAPairKeyAuthentication) attribute;
		}
		PublicKey readPublicKey = rsaPairKey.getPublicKey();
		
		
		// AES session
		AESKeyExchangeDTO keyExchange = new AESKeyExchangeDTO();
		String encode = RSAClientEncryptionUtils.encrypt(aesPass, readPublicKey);
		keyExchange.setEncode(encode);
		authController.saveAES(session, keyExchange);
		
		
		
		// api request
		ApiRequestMessage2 req = new ApiRequestMessage2();
		AESKeyAuthentication aesKey = new AESKeyAuthentication(aesPass);
		String encrypt = AESEncryptionUtils.encrypt("Dara", aesKey );
		req.setName(encrypt);
		ObjectMapper clientOMapper = new ObjectMapper();
		String writeValueAsString = clientOMapper.writeValueAsString(req);
		
		
		// server read data & and make response
		ObjectMapper objectMapper = new ObjectMapper();
		CipherDataObjectMapperConfigurationAdaptor adaptor = 
				new CipherDataObjectMapperConfigurationAdaptor();

		adaptor.configureObjectMapper(objectMapper);
		req = objectMapper.readValue(writeValueAsString, ApiRequestMessage2.class);
		
		MessageController controller = new MessageController();
		ResponseMessage<ApiResponseMessage2> secureMessage = controller.decrypt2(req);
		
		
		String value = objectMapper.writeValueAsString(secureMessage);
			
		assertNotNull(value);
		System.out.println(value);
	}

}
