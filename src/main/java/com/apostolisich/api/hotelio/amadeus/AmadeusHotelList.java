package com.apostolisich.api.hotelio.amadeus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AmadeusHotelList {
	
	@Autowired
	private AmadeusAccessTokenGetter accessTokenCreator;
	
	public AmadeusHotelListResponse getAvailableHotels() {
		String accessToken = accessTokenCreator.getAccessToken();
		
		WebClient webClient = WebClient.create("https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-geocode?latitude=41.397158&longitude=2.160873&radius=5&radiusUnit=KM&hotelSource=ALL");
		AmadeusHotelListResponse response = webClient.get()
								   .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
								   .retrieve()
								   .bodyToMono(AmadeusHotelListResponse.class)
								   .block();
		
		return response;
	}

}
