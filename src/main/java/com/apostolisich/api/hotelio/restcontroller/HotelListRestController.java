package com.apostolisich.api.hotelio.restcontroller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.hotellist.GetHotelListRequest;
import com.apostolisich.api.hotelio.hotellist.GetHotelListResponse;
import com.apostolisich.api.hotelio.hotellist.HotelListService;

@RestController
public class HotelListRestController {

	private final Logger LOG = LoggerFactory.getLogger(HotelListRestController.class);
	
	private final List<HotelListService> hotelListServices;
	
	/*
	 * Automatically injects all the subclasses of the HotelListService class
	 */
	@Autowired
	public HotelListRestController(List<HotelListService> hotelListServices) {
		this.hotelListServices = hotelListServices;
	}
	
	@GetMapping("/list")
	public List<GetHotelListResponse> getHotelList(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int radius) throws InterruptedException, ExecutionException {
		GetHotelListRequest hotelListRequest = new GetHotelListRequest(latitude, longitude, radius);

		List<GetHotelListResponse> hotelListResponses = new ArrayList<>(hotelListServices.size());
		hotelListServices.forEach( hotelListService -> hotelListResponses.add(hotelListService.getHotelList(hotelListRequest)));

		return hotelListResponses;
	}

}
