package com.apostolisich.api.hotelio.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.amadeus.AmadeusHotelListResponse;
import com.apostolisich.api.hotelio.amadeus.AmadeusHotelListService;
import com.apostolisich.api.hotelio.request.HotelListRequest;

@RestController
@RequestMapping("/hotel")
public class HotelListRestController {
	
	@Autowired
	private AmadeusHotelListService amadeusHotelList;
	
	@PostMapping("/list")
	public AmadeusHotelListResponse getHotelList(@RequestBody HotelListRequest hotelListRequest) {
		return amadeusHotelList.getHotelList(hotelListRequest);
	}

}
