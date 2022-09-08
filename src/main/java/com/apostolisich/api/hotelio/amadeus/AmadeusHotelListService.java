package com.apostolisich.api.hotelio.amadeus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apostolisich.api.hotelio.request.GetHotelListRequest;
import com.apostolisich.api.hotelio.response.GetHotelListResponse;

@Service
public class AmadeusHotelListService {
	
	private final String HOTEL_LIST_BASE_URL = "https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-geocode?";
	
	@Autowired
	private AmadeusAccessTokenService accessTokenCreator;
	
	public GetHotelListResponse getHotelList(GetHotelListRequest hotelListRequest) {
		String accessToken = accessTokenCreator.getAccessToken();
		
		WebClient webClient = WebClient.create(buildAmadeusHotelListUrl(hotelListRequest));
		AmadeusHotelListResponse amadeusHotelListResponse = webClient.get()
																	 .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
																	 .retrieve()
																	 .bodyToMono(AmadeusHotelListResponse.class)
																	 .block();
		
		return buildGetHotelListResponse(amadeusHotelListResponse);
	}
	
	/**
	 * Creates the URL for Amadeus Hotel List request based on the given {@code HotelListRequest}.
	 * 
	 * @param hotelListRequest the body of the HotelList request
	 * @return the created URL for the Amadeus Hotel List request
	 */
	private String buildAmadeusHotelListUrl(GetHotelListRequest hotelListRequest) {
		StringBuilder amadeusHotelListUrlBuilder = new StringBuilder();
		
		amadeusHotelListUrlBuilder.append(HOTEL_LIST_BASE_URL);
		amadeusHotelListUrlBuilder.append("latitude=");
		amadeusHotelListUrlBuilder.append(String.valueOf(hotelListRequest.getLatitude()));
		amadeusHotelListUrlBuilder.append("&longitude=");
		amadeusHotelListUrlBuilder.append(String.valueOf(hotelListRequest.getLongitude()));
		amadeusHotelListUrlBuilder.append("&radius=");
		amadeusHotelListUrlBuilder.append(String.valueOf(hotelListRequest.getRadius()));
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
		GetHotelListResponse getHotelListResponsePart = new GetHotelListResponse("amadeus");
		
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
