package com.apostolisich.api.hotelio.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersRequest;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse;
import com.apostolisich.api.hotelio.provider.amadeus.AmadeusHotelOffersService;

@RestController
public class HotelOffersRestController {
	
	@Autowired
	private AmadeusHotelOffersService hotelOffersService;
	
	@PostMapping("/offers")
	public GetHotelOffersResponse getHotelOffers(@RequestBody GetHotelOffersRequest hotelOffersRequest) {
		if(!"amadeus".equals(hotelOffersRequest.getProvider()))
			throw new RuntimeException("Hotel offers are currently supported only for Amadeus");
		
		return hotelOffersService.getHotelOffers(hotelOffersRequest);
	}

}
