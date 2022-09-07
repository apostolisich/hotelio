package com.apostolisich.api.hotelio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.amadeus.AccessTokenCreator;
import com.apostolisich.api.hotelio.amadeus.AccessTokenResponse;

@RestController
@RequestMapping("/hotel")
public class DemoController {
	
	@Autowired
	private AccessTokenCreator tokenCreator;
	
	
	@GetMapping("/list")
	public AccessTokenResponse test() {
		return tokenCreator.getAccessToken();
	}

}
