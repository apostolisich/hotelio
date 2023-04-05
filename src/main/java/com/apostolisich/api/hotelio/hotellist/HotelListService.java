package com.apostolisich.api.hotelio.hotellist;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.apostolisich.api.hotelio.provider.amadeus.AmadeusHotelListService;
import com.apostolisich.api.hotelio.redis.RedisUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

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

	private static final Logger LOG = LoggerFactory.getLogger(AmadeusHotelListService.class);
	
	/**
	 * Returns all the available hotels based on the provided {@code GetHotelListRequest}, either
	 * directly from the provider or from the cache if they exist there.
	 * 
	 * @param hotelListRequest the body of the {@code GetHotelListRequest} request
	 * @return the constructed {@code GetHotelListResponse} of all the available hotels
	 */
	public final CompletableFuture<GetHotelListResponse> getHotelList(GetHotelListRequest hotelListRequest) {
		String providerName = getProviderName();
		RedisUtilityService redisUtilityService = getRedisUtilityService();
		String cacheKey = providerName + hotelListRequest.getCacheKey();

		GetHotelListResponse getHotelListResponse = redisUtilityService.findHotelListResponseByKey(cacheKey);
		if(getHotelListResponse != null) {
			LOG.debug("Fetching hotel list from Redis: " + providerName);
			return CompletableFuture.completedFuture(getHotelListResponse);
		} else {
			LOG.debug("Fetching hotel list via API: " + providerName);
			return getHotelListFromProvider(hotelListRequest).thenApply(hotelListResponse -> {
				if(hotelListResponse != null) {
					redisUtilityService.save(cacheKey, hotelListResponse);
				}

				return hotelListResponse;
			});
		}
	}

	/**
	 * Returns the name of the current provider.
	 *
	 * @return the name of the current provider
	 */
	protected abstract String getProviderName();

	/**
	 * Returns a {@code RedisUtilityService} object that provides access to the
	 * redis cache.
	 *
	 * @return a {@code RedisUtilityService} object that provides access to the
	 * 	 	   redis cache.
	 */
	protected abstract RedisUtilityService getRedisUtilityService();
	
	/**
	 * Returns all the available hotels from the provider.
	 * 
	 * @param hotelListRequest the body of the {@code GetHotelListRequest} request
	 * @return he constructed {@code GetHotelListResponse} of all the available hotels
	 */
	@Async
	protected abstract CompletableFuture<GetHotelListResponse> getHotelListFromProvider(GetHotelListRequest hotelListRequest);

}
