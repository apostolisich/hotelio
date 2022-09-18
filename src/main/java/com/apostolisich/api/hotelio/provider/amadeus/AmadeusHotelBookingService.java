package com.apostolisich.api.hotelio.provider.amadeus;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apostolisich.api.hotelio.dao.HotelBookingDAO;
import com.apostolisich.api.hotelio.exception.OfferNotFoundException;
import com.apostolisich.api.hotelio.hotelbooking.CreateHotelBookingRequest;
import com.apostolisich.api.hotelio.hotelbooking.CreateHotelBookingResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmadeusHotelBookingService {
	
	private static final Logger LOGGER = LogManager.getLogger(AmadeusHotelBookingService.class);
	
	private HotelBookingDAO hotelBookingDAO;
	private AmadeusAccessTokenService accessTokenService;
	
	@Autowired
	public AmadeusHotelBookingService(HotelBookingDAO hotelBookingDAO, AmadeusAccessTokenService accessTokenService) {
		this.hotelBookingDAO = hotelBookingDAO;
		this.accessTokenService = accessTokenService;
	}
	
	/**
	 * Fetches the details of the selected offer and creates a new booking record in the database
	 * based on the latter.
	 * 
	 * @param hotelBookingRequest a {@code CreateHotelBookingRequest} object
	 * @return the created booking
	 */
	public CreateHotelBookingResponse createHotelBooking(CreateHotelBookingRequest hotelBookingRequest) {
		String offerId = hotelBookingRequest.getOfferId();
		JsonNode hotelOfferDetails = getHotelOfferDetails(offerId);
		
		String hotelName = hotelOfferDetails.get("hotel").get("name").asText();
		JsonNode offerDetails = hotelOfferDetails.get("offers").get(0);
		String checkIn = offerDetails.get("checkInDate").asText();
		String checkOut = offerDetails.get("checkOutDate").asText();
		String roomDescription = offerDetails.get("room").get("description").get("text").asText();
		JsonNode offerPriceDetails = offerDetails.get("price");
		String currency = offerPriceDetails.get("currency").asText();
		BigDecimal totalAmount = new BigDecimal(offerPriceDetails.get("total").asText());
		
		return hotelBookingDAO.createNewBooking(totalAmount, currency, hotelName, checkIn, checkOut, roomDescription, hotelBookingRequest);
	}
	
	/**
	 * Gets the offer details for the given offer id from Amadeus.
	 * 
	 * @param offerId the offer that the user wants to book
	 * @return the created {@code JsonNode} response
	 */
	private JsonNode getHotelOfferDetails(String offerId) {
		String accessToken = accessTokenService.getAccessToken();
		
		try {
			WebClient client = WebClient.create("https://test.api.amadeus.com/v3/shopping/hotel-offers/" + offerId);
			String response = client.get()
								    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
								    .retrieve()
								    .bodyToMono(String.class)
								    .block();
		
		
			JsonNode hotelOfferDetails = new ObjectMapper().readTree(response).get("data");
			return hotelOfferDetails;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		
		throw new OfferNotFoundException("Hotel offer couldn't be found in provider's system");
	}

}
