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

		Queue<CompletableFuture<GetHotelListResponse>> completablesQueue = new LinkedList<>();
		hotelListServices.forEach( hotelListService -> completablesQueue.add(hotelListService.getHotelList(hotelListRequest)));

		return getCompletedHotelListResponses(completablesQueue);
	}

	/**
	 * Checks the given {@code Queue} of {@code CompetableFuture} objects until
	 * all of them are completed, stores their response and removes them from the
	 * {@code Queue}.
	 *
	 * @param completablesQueue a {@code Queue} of {@code CompetableFuture} objects
	 * 						   each of which will return the {@code GetHoteListResponse}
	 * 						   of a single provider
	 * @return a {@code List} of {@code GetHotelListResponse} objects for all providers
	 * 		   that returned results for the current search
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private List<GetHotelListResponse> getCompletedHotelListResponses(Queue<CompletableFuture<GetHotelListResponse>> completablesQueue) throws InterruptedException, ExecutionException {
		List<GetHotelListResponse> hotelListResponses = new ArrayList<>(completablesQueue.size());

		while (!completablesQueue.isEmpty()) {
			CompletableFuture<GetHotelListResponse> hotelListCompletable = completablesQueue.peek();

			if (hotelListCompletable.isDone()) {
				GetHotelListResponse hotelListResponse = hotelListCompletable.get();
				hotelListResponses.add(hotelListResponse);
				LOG.debug("Fetching hotel list completed: " + hotelListResponse.getProvider());

				completablesQueue.remove();
			}
		}

		return hotelListResponses;
	}

}