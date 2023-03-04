package com.apostolisich.api.hotelio.hotellist;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import com.apostolisich.api.hotelio.redis.RedisUtilityService;

/**
 * A class the defines how the {@code GetHotelListResponse} will be populated
 * for a specific provider, with the results coming directly from the provider
 * via an HTTP request or from the internal Redis cache if they exist there.
 * 
 * The subclasses of this class are automatically injected in the HotelListRestController
 * providing an easy way to define new providers by just extending this class.
 * 
 * {@link com.apostolisich.api.hotelio.restcontroller.HotelListRestController#HotelListRestController(List)}
 */
public abstract class HotelListService {
	
	private final String providerName;
	private final RedisUtilityService redisUtilityService;
	
	public HotelListService(String providerName, RedisUtilityService redisUtilityService) {
		this.providerName = providerName;
		this.redisUtilityService = redisUtilityService;
	}
	
	/**
	 * Returns all the available hotels based on the provided {@code GetHotelListRequest}, either
	 * directly from the provider or from the cache if they exist there.
	 * 
	 * @param hotelListRequest the body of the {@code GetHotelListRequest} request
	 * @return the constructed {@code GetHotelListResponse} of all the available hotels
	 */
	@Async
	public final CompletableFuture<GetHotelListResponse> getHotelList(GetHotelListRequest hotelListRequest) {
		String cacheKey = providerName + hotelListRequest.getCacheKey();
		
		GetHotelListResponse getHotelListResponse = redisUtilityService.findHotelListResponseByKey(cacheKey);
		if(getHotelListResponse == null) {
			getHotelListResponse = getHotelListFromProvider(hotelListRequest);
			redisUtilityService.save(cacheKey, getHotelListResponse);
		}
		
		return CompletableFuture.completedFuture(getHotelListResponse);
	}
	
	/**
	 * Returns all the available hotels from the provider.
	 * 
	 * @param hotelListRequest the body of the {@code GetHotelListRequest} request
	 * @return he constructed {@code GetHotelListResponse} of all the available hotels
	 */
	protected abstract GetHotelListResponse getHotelListFromProvider(GetHotelListRequest hotelListRequest);

}
