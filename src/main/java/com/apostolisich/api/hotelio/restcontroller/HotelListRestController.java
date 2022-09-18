package com.apostolisich.api.hotelio.restcontroller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apostolisich.api.hotelio.hotellist.GetHotelListRequest;
import com.apostolisich.api.hotelio.hotellist.GetHotelListResponse;
import com.apostolisich.api.hotelio.hotellist.HotelListService;

@RestController
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
	public List<GetHotelListResponse> getHotelList(@RequestBody GetHotelListRequest hotelListRequest) throws InterruptedException, ExecutionException {
		Queue<CompletableFuture<GetHotelListResponse>> competablesQueue = new LinkedList<>();
		
		hotelListServices.forEach( hotelListService -> {
			competablesQueue.add(hotelListService.getHotelList(hotelListRequest));
		});
		
		List<GetHotelListResponse> hotelListResponses = getCompletedHotelListResponses(competablesQueue);
		
		return hotelListResponses;
	}

	/**
	 * Checks the given {@code Queue} of {@code CompetableFuture} objects until
	 * all of them are completed, store their response and remove them from the
	 * {@code Queue}.
	 * 
	 * @param competablesQueue a {@code Queue} of {@code CompetableFuture} objects
	 * 						   that will return the {@code GetHoteListResponse} of
	 * 						   a single provider
	 * @return a {@code List} of {@code GetHotelListResponse} objects for all providers
	 * 		   that returned results for the current search
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private List<GetHotelListResponse> getCompletedHotelListResponses(Queue<CompletableFuture<GetHotelListResponse>> competablesQueue) throws InterruptedException, ExecutionException {
		List<GetHotelListResponse> hotelListResponses = new ArrayList<>(hotelListServices.size());
		
		while(!competablesQueue.isEmpty()) {
			CompletableFuture<GetHotelListResponse> hotelListCompetable = competablesQueue.peek();
			
			if(hotelListCompetable.isDone()) {
				GetHotelListResponse hotelListResponse = hotelListCompetable.get();
				if(hotelListResponse != null) {
					hotelListResponses.add(hotelListResponse);
				}
				
				competablesQueue.remove();
			}
		}
		
		return hotelListResponses;
	}

}
