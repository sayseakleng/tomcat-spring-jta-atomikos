package com.mcnc.mbanking.auth.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mcnc.mbanking.auth.dto.AESKeyExchangeDTO;

@Component
public class AESKeyExchangeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AESKeyExchangeDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "encode", "Encode cannot be null");
		
	}

}
