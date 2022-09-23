package com.apostolisich.api.hotelio.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.exception.UnsupportedProviderOperationException;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersRequest;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse;
import com.apostolisich.api.hotelio.provider.amadeus.AmadeusHotelOffersService;

@RestController
public class HotelOffersRestController {
	
	@Autowired
	private AmadeusHotelOffersService hotelOffersService;
	
	@GetMapping("/offers")
	public GetHotelOffersResponse getHotelOffers(@RequestParam String hotelId, @RequestParam String provider, @RequestParam String checkInDate,
												 @RequestParam String checkOutDate, @RequestParam int adults) {
		GetHotelOffersRequest hotelOffersRequest = new GetHotelOffersRequest(hotelId, provider, checkInDate, checkOutDate, adults);
		
		if(!"amadeus".equals(hotelOffersRequest.getProvider()))
			throw new UnsupportedProviderOperationException("Hotel offers are currently supported only for Amadeus");
		
		return hotelOffersService.getHotelOffers(hotelOffersRequest);
	}

}
