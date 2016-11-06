package com.mcnc.mbanking.auth.secure.mapper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;
import com.mcnc.mbanking.auth.exception.CipherDataSerializerException;
import com.mcnc.mbanking.auth.exception.InvalidAESKeyAuthenticationException;
import com.mcnc.mbanking.auth.util.AESEncryptionUtils;
import com.mcnc.mbanking.auth.util.Consts;

public class CipherDataSerializer extends StdSerializer<Object> {
	private static final long serialVersionUID = -8389202880537635878L;

	private static final Logger logger = LoggerFactory.getLogger(CipherDataSerializer.class);
	

	public CipherDataSerializer() {
		super(Object.class);
	}


	@Override
	public void serialize(Object oringalValue, JsonGenerator gen, SerializerProvider provider) throws IOException {
		String parseValue = null;
		
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
			if(oringalValue != null) {
				parseValue = AESEncryptionUtils.encrypt(oringalValue.toString(), aesKey);
				
				if(parseValue ==  null) {
					logger.error("Cannot encrypt data");
					throw new CipherDataSerializerException(gen.getOutputContext().getCurrentName());
				}
			}
		}
		else {
			logger.error("Cannot read AES Key from session");
			throw new InvalidAESKeyAuthenticationException();
		}
		
		gen.writeString(parseValue);
	}
	
	
	private HttpServletRequest getServletRequest() {
		final ServletRequestAttributes requestServletRequestAttributes = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		return requestServletRequestAttributes != null ? requestServletRequestAttributes.getRequest() : null;
	}

}
