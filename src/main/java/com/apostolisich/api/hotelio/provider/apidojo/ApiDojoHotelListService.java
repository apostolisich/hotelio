package com.apostolisich.api.hotelio.provider.apidojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apostolisich.api.hotelio.hotellist.GetHotelListRequest;
import com.apostolisich.api.hotelio.hotellist.GetHotelListResponse;
import com.apostolisich.api.hotelio.hotellist.HotelListService;
import com.apostolisich.api.hotelio.redis.RedisUtilityService;

@Service
public class ApiDojoHotelListService extends HotelListService {
	
	private static final String PROVIDER = "apidojo";
	
	@Value("${apidojo.api.key}")
	private String rapidApiKey;
	
	@Value("${apidojo.api.host}")
	private String rapidApiHost;

	private final RedisUtilityService redisUtilityService;

	@Autowired
	public ApiDojoHotelListService(RedisUtilityService redisUtilityService) {
		this.redisUtilityService = redisUtilityService;
	}

	@Override
	protected String getProviderName() {
		return PROVIDER;
	}

	@Override
	protected RedisUtilityService getRedisUtilityService() {
		return redisUtilityService;
	}

	@Override
	protected GetHotelListResponse getHotelListFromProvider(GetHotelListRequest hotelListRequest) {
		ApiDojoHotelListResponse apiDojoHotelListResponse = getApiDojoHotelListResponse(hotelListRequest);
	
		return buildGetHotelListResponse(apiDojoHotelListResponse);
	}
	
	/**
	 * Sends a HotelList request to ApiDojo based on the given {@code GetHotelListRequest}
	 * and gets all the available hotels based on this criteria.
	 * 
	 * @param hotelListRequest the body of the {@code GetHotelListRequest} request
	 * @return the HotelList response from ApiDojo
	 */
	private ApiDojoHotelListResponse getApiDojoHotelListResponse(GetHotelListRequest hotelListRequest) {
		WebClient client = WebClient.create(buildApiDojoHotelListUrl(hotelListRequest));
		
		ApiDojoHotelListResponse apiDojoHotelResponse = client.get()
															  .header("X-RapidAPI-Key", rapidApiKey)
															  .header("X-RapidAPI-Host", rapidApiHost)
															  .retrieve()
															  .bodyToMono(ApiDojoHotelListResponse.class)
															  .block();
		
		return apiDojoHotelResponse;
	}
	
	/**
	 * Creates the URL for ApiDojo Hotel List request based on the given {@code HotelListRequest}.
	 * 
	 * @param hotelListRequest the body of the GetHotelList request
	 * @return the created URL for the ApiDojo Hotel List request
	 */
	private String buildApiDojoHotelListUrl(GetHotelListRequest hotelListRequest) {
		StringBuilder apiDojoHotelListUrlBuilder = new StringBuilder();
		
		apiDojoHotelListUrlBuilder.append("https://travel-advisor.p.rapidapi.com/hotels/list-by-latlng?");
		apiDojoHotelListUrlBuilder.append("latitude=");
		apiDojoHotelListUrlBuilder.append(hotelListRequest.getLatitude());
		apiDojoHotelListUrlBuilder.append("&longitude=");
		apiDojoHotelListUrlBuilder.append(hotelListRequest.getLongitude());
		apiDojoHotelListUrlBuilder.append("&distance=");
		apiDojoHotelListUrlBuilder.append(hotelListRequest.getRadius());
		
		return apiDojoHotelListUrlBuilder.toString();
	}
	
	/**
	 * Parses the provided {@code ApiDojoHotelListResponse}, extracts the returned
	 * hotel entries and adds them to the {@code GetHotelListResponse}.
	 * 
	 * @param response an {@code ApiDojoHotelListResponse} object
	 * @return the created {@code GetHotelListResponse}
	 */
	private GetHotelListResponse buildGetHotelListResponse(ApiDojoHotelListResponse response) {
		GetHotelListResponse getHotelListResponsePart = new GetHotelListResponse(PROVIDER);
		
		response.getData().forEach( apiDojoHotelEntry -> {
			String id = apiDojoHotelEntry.getHotelId();
			String name = apiDojoHotelEntry.getName();
			double latitude = apiDojoHotelEntry.getLatitude();
			double longitude = apiDojoHotelEntry.getLongitude();
			
			//We skip invalid entries
			if(name == null || latitude == 0.0 || longitude == 0.0)
				return;
			
			getHotelListResponsePart.addHotelEntry(id, null, name, latitude, longitude);
		});
		
		return getHotelListResponsePart;
	}

}
