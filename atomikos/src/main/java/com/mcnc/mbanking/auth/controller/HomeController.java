package com.mcnc.mbanking.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	

	@GetMapping("/")
	public String getHomeApi() {
		
		return "OTP Server started";
		
	}
	
	

}
