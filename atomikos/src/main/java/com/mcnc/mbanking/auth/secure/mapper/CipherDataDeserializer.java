package com.mcnc.mbanking.auth.secure.mapper;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;
import com.mcnc.mbanking.auth.exception.CipherDataDeserializerException;
import com.mcnc.mbanking.auth.exception.InvalidAESKeyAuthenticationException;
import com.mcnc.mbanking.auth.util.AESEncryptionUtils;
import com.mcnc.mbanking.auth.util.Consts;

public class CipherDataDeserializer extends StdDeserializer<Object> {
	private static final long serialVersionUID = 5939013906472490609L;

	private static final Logger logger = LoggerFactory.getLogger(CipherDataDeserializer.class);
	
	private static ObjectMapper oMapper = new ObjectMapper();

	public CipherDataDeserializer() {
		super(Object.class);
	}

	
	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Object parseValue = null;
		

		HttpServletRequest servletRequest = getServletRequest();
		
		AESKeyAuthentication aesKey = null;
		if(servletRequest != null && servletRequest.getSession() != null) {
			HttpSession session = servletRequest.getSession();
			
			Object attribute = session.getAttribute(Consts.AES_SESSION_KEY); 
			
			if(attribute instanceof AESKeyAuthentication) {
				aesKey = (AESKeyAuthentication) attribute;
			}
			
		}
		
		if(aesKey != null) {
			
			String originalValue = p.getValueAsString();
			if(originalValue != null) {
				String sValue = AESEncryptionUtils.decrypt(originalValue, aesKey);
				parseValue = parse(sValue, p.getParsingContext());
				
				if(parseValue ==  null) {
					logger.error("Cannot decrypt data");
					throw new CipherDataDeserializerException(p.getCurrentName());
				}
			}
			
		}
		else {
			logger.error("Cannot read AES Key from session");
			throw new InvalidAESKeyAuthenticationException();
		}
		
		return parseValue;
	}
	
	
	private Object parse(String sValue, JsonStreamContext parsingContext) {
		Object result = null;
		
		try {
			Field declaredField = parsingContext.getCurrentValue().getClass()
					.getDeclaredField(parsingContext.getCurrentName());
			Class<?> type = declaredField.getType();
			
			result = oMapper.convertValue(sValue, type);
			
		} catch (Exception e) {
			logger.error("Cannot read field meta-data with @CipherData");
		}
		
		
		return result;
	}
	
	
	private HttpServletRequest getServletRequest() {
		final ServletRequestAttributes requestServletRequestAttributes = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		return requestServletRequestAttributes != null ? requestServletRequestAttributes.getRequest() : null;
	}
}
