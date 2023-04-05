package com.apostolisich.api.hotelio.provider.amadeus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apostolisich.api.hotelio.hotellist.GetHotelListRequest;
import com.apostolisich.api.hotelio.hotellist.GetHotelListResponse;
import com.apostolisich.api.hotelio.hotellist.HotelListService;
import com.apostolisich.api.hotelio.redis.RedisUtilityService;

import java.util.concurrent.CompletableFuture;

@Service
public class AmadeusHotelListService extends HotelListService {

	private static final String PROVIDER = "amadeus";

	private final RedisUtilityService redisUtilityService;

	private final AmadeusAccessTokenService accessTokenCreator;
	
	@Autowired
	public AmadeusHotelListService(RedisUtilityService redisUtilityService, AmadeusAccessTokenService accessTokenCreator) {
		this.redisUtilityService = redisUtilityService;
		this.accessTokenCreator = accessTokenCreator;
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
	protected CompletableFuture<GetHotelListResponse> getHotelListFromProvider(GetHotelListRequest hotelListRequest) {
		return CompletableFuture.supplyAsync(() -> buildGetHotelListResponse(getHotelListResponseFromAmadeus(hotelListRequest)));
	}

	/**
	 * Sends a HotelList request to Amadeus based on the given {@code GetHotelListRequest}
	 * and gets all the available hotels based on these criteria.
	 * 
	 * @param hotelListRequest the body of the {@code GetHotelListRequest} request
	 * @return the HotelList response from Amadeus
	 */
	private AmadeusHotelListResponse getHotelListResponseFromAmadeus(GetHotelListRequest hotelListRequest) {
		String accessToken = accessTokenCreator.getAccessToken();
		
		WebClient webClient = WebClient.create(buildAmadeusHotelListUrl(hotelListRequest));
		AmadeusHotelListResponse amadeusHotelListResponse = webClient.get()
																	 .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
																	 .retrieve()
																	 .bodyToMono(AmadeusHotelListResponse.class)
																	 .block();
		
		return amadeusHotelListResponse;
	}
	
	/**
	 * Creates the URL for Amadeus Hotel List request based on the given {@code HotelListRequest}.
	 * 
	 * @param hotelListRequest the body of the GetHotelList request
	 * @return the created URL for the Amadeus Hotel List request
	 */
	private String buildAmadeusHotelListUrl(GetHotelListRequest hotelListRequest) {
		StringBuilder amadeusHotelListUrlBuilder = new StringBuilder();
		
		amadeusHotelListUrlBuilder.append("https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-geocode?");
		amadeusHotelListUrlBuilder.append("latitude=");
		amadeusHotelListUrlBuilder.append(hotelListRequest.getLatitude());
		amadeusHotelListUrlBuilder.append("&longitude=");
		amadeusHotelListUrlBuilder.append(hotelListRequest.getLongitude());
		amadeusHotelListUrlBuilder.append("&radius=");
		amadeusHotelListUrlBuilder.append(hotelListRequest.getRadius());
		amadeusHotelListUrlBuilder.append("&radiusUnit=KM");
		amadeusHotelListUrlBuilder.append("&hotelSource=ALL");
		
		return amadeusHotelListUrlBuilder.toString();
	}
	
	/**
	 * Parses the provided {@code AmadeusHotelListResponse}, extracts the returned
	 * hotel entries and adds them to the {@code GetHotelListResponse}.
	 * 
	 * @param response an {@code AmadeusHotelListResponse} object
	 * @return the created {@code GetHotelListResponse}
	 */
	private GetHotelListResponse buildGetHotelListResponse(AmadeusHotelListResponse response) {
		GetHotelListResponse getHotelListResponsePart = new GetHotelListResponse(PROVIDER);
		
		response.getData().forEach( amadeusHotelEntry -> {
			String id = amadeusHotelEntry.getHotelId();
			String iataCode = amadeusHotelEntry.getIataCode();
			String name = amadeusHotelEntry.getName();
			double latitude = amadeusHotelEntry.getGeoCode().getLatitude();
			double longitude = amadeusHotelEntry.getGeoCode().getLongitude();
			
			getHotelListResponsePart.addHotelEntry(id, iataCode, name, latitude, longitude);
		});
		
		return getHotelListResponsePart;
	}

}
