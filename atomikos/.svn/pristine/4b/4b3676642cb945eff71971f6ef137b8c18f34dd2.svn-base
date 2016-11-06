package com.mcnc.mbanking.auth.controller;

import javax.servlet.http.HttpSession;

import org.cryptonode.jncryptor.CryptorException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mcnc.mbanking.auth.domain.AESKeyAuthentication;
import com.mcnc.mbanking.auth.dto.ApiRequestMessage;
import com.mcnc.mbanking.auth.dto.ApiRequestMessage2;
import com.mcnc.mbanking.auth.dto.ApiResponseMessage;
import com.mcnc.mbanking.auth.dto.ApiResponseMessage2;
import com.mcnc.mbanking.auth.dto.ResponseCode;
import com.mcnc.mbanking.auth.dto.ResponseMessage;
import com.mcnc.mbanking.auth.dto.ResponseMessageBuilder;
import com.mcnc.mbanking.auth.util.AESEncryptionUtils;
import com.mcnc.mbanking.auth.util.Consts;

@RestController
public class MessageController {
	
	
	
	@PostMapping("/api/text")
	public ResponseMessage<ApiResponseMessage> decrypt(HttpSession httpSession,
			@RequestBody ApiRequestMessage req) throws CryptorException {
		ApiResponseMessage result = null;
		ResponseCode code = null;
		
		Object attribute = httpSession.getAttribute(Consts.AES_SESSION_KEY); 
		if(attribute != null && attribute instanceof AESKeyAuthentication) {
			
			AESKeyAuthentication aesKey = (AESKeyAuthentication) attribute;
			
		    String name = AESEncryptionUtils.decrypt(req.getName(), aesKey);
			
			if(StringUtils.isEmpty(name)) {
				code = ResponseCode.E0003;
			}
			else {
				result = new ApiResponseMessage();
				
				String encryptResponse = AESEncryptionUtils.encrypt(String.format("Your name is %s", name), 
						aesKey);
				
				result.setEncryptText(encryptResponse);
			}
			
		}
		else {
			code = ResponseCode.E0002;
		}
		
		
		return ResponseMessageBuilder.instance(result)
				.addCode(code)
				.build();
	}
	
	
	
	/**
	 * Spring Based
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping("/api/text2")
	public ResponseMessage<ApiResponseMessage2> decrypt2(
			@RequestBody ApiRequestMessage2 req) {
		

		ApiResponseMessage2 msg = new ApiResponseMessage2();
		msg.setEncryptText(String.format("Your name is %s", req.getName()));
		
		return ResponseMessageBuilder.instance(msg)
				.build();
	}
	
}
