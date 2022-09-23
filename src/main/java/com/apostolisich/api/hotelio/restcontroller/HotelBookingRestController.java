package com.apostolisich.api.hotelio.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.dao.entity.Booking;
import com.apostolisich.api.hotelio.exception.UnsupportedProviderOperationException;
import com.apostolisich.api.hotelio.hotelbooking.CreateHotelBookingRequest;
import com.apostolisich.api.hotelio.hotelbooking.CreateHotelBookingResponse;
import com.apostolisich.api.hotelio.hotelbooking.GetHotelBookingService;
import com.apostolisich.api.hotelio.provider.amadeus.AmadeusHotelBookingService;

@RestController
public class HotelBookingRestController {
	
	private AmadeusHotelBookingService amadeusHotelBookingService;
	private GetHotelBookingService getHotelBookingService;
	
	@Autowired
	public HotelBookingRestController(AmadeusHotelBookingService amadeusHotelBookingService, GetHotelBookingService getHotelBookingService) {
		this.amadeusHotelBookingService = amadeusHotelBookingService;
		this.getHotelBookingService = getHotelBookingService;
	}
	
	@GetMapping("/booking")
	private Booking retrieveBooking(@RequestParam String bookingReference) {
		return getHotelBookingService.retrieveBooking(bookingReference);
	}
	
	@PostMapping("/booking")
	private CreateHotelBookingResponse createBooking(@RequestBody CreateHotelBookingRequest hotelBookingRequest) {
		if(!"amadeus".equals(hotelBookingRequest.getProvider()))
			throw new UnsupportedProviderOperationException("Hotel booking is currently supported only for Amadeus");
		
		CreateHotelBookingResponse hotelBookingResponse = amadeusHotelBookingService.createHotelBooking(hotelBookingRequest);
		return hotelBookingResponse;
	}

}
