package com.apostolisich.api.hotelio.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.hotellist.GetHotelListRequest;
import com.apostolisich.api.hotelio.hotellist.GetHotelListResponse;
import com.apostolisich.api.hotelio.hotellist.HotelListService;

@RestController
@RequestMapping("/hotel")
public class HotelListRestController {
	
	private List<HotelListService> hotelListServices;
	
	/*
	 * Automatically injects all the sub-classes of the HotelListService class
	 */
	@Autowired
	public HotelListRestController(List<HotelListService> hotelListServices) {
		this.hotelListServices = hotelListServices;
	}
	
	@PostMapping("/list")
	public List<GetHotelListResponse> getHotelList(@RequestBody GetHotelListRequest hotelListRequest) {
		List<GetHotelListResponse> hotelListResponses = new ArrayList<>(hotelListServices.size());
		
		hotelListServices.forEach( hotelListService -> {
			hotelListResponses.add(hotelListService.getHotelList(hotelListRequest));
		});
		
		return hotelListResponses;
	}

}
