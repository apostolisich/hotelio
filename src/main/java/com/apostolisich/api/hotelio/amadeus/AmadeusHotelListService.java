package com.apostolisich.api.hotelio.amadeus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apostolisich.api.hotelio.request.HotelListRequest;

@Service
public class AmadeusHotelListService {
	
	private static final String HOTEL_LIST_BASE_URL = "https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-geocode?";
	
	@Autowired
	private AmadeusAccessTokenGetter accessTokenCreator;
	
	public AmadeusHotelListResponse getHotelList(HotelListRequest hotelListRequest) {
		String accessToken = accessTokenCreator.getAccessToken();
		
		WebClient webClient = WebClient.create(buildHotelListUrl(hotelListRequest));
		AmadeusHotelListResponse response = webClient.get()
								   .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
								   .retrieve()
								   .bodyToMono(AmadeusHotelListResponse.class)
								   .block();
		
		return response;
	}
	
	/**
	 * Creates the URL for Amadeus Hotel List request based on the given {@code HotelListRequest}.
	 * 
	 * @param hotelListRequest the body of the HotelList request
	 * @return the created URL for the Amadeus Hotel List request
	 */
	private String buildHotelListUrl(HotelListRequest hotelListRequest) {
		StringBuilder hotelListUrlBuilder = new StringBuilder();
		
		hotelListUrlBuilder.append(HOTEL_LIST_BASE_URL);
		hotelListUrlBuilder.append("latitude=");
		hotelListUrlBuilder.append(String.valueOf(hotelListRequest.getLatitude()));
		hotelListUrlBuilder.append("&longitude=");
		hotelListUrlBuilder.append(String.valueOf(hotelListRequest.getLongitude()));
		hotelListUrlBuilder.append("&radius=");
		hotelListUrlBuilder.append(String.valueOf(hotelListRequest.getRadius()));
		hotelListUrlBuilder.append("&radiusUnit=KM");
		hotelListUrlBuilder.append("&hotelSource=ALL");
		
		return hotelListUrlBuilder.toString();
	}

}
