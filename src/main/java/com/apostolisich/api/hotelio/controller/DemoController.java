package com.apostolisich.api.hotelio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.amadeus.AmadeusHotelListResponse;
import com.apostolisich.api.hotelio.amadeus.AmadeusHotelList;

@RestController
@RequestMapping("/hotel")
public class DemoController {
	
	@Autowired
	private AmadeusHotelList hotelList;
	
	@GetMapping("/list")
	public AmadeusHotelListResponse test() {
		return hotelList.getAvailableHotels();
	}

}
