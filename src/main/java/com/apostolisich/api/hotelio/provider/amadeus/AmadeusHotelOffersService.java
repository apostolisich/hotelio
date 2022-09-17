package com.apostolisich.api.hotelio.provider.amadeus;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersRequest;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse.HotelOffer;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse.HotelOfferPrice;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse.HotelOfferRoom;
import com.apostolisich.api.hotelio.hoteloffers.GetHotelOffersResponse.HotelOfferTaxItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmadeusHotelOffersService {
	
	@Autowired
	private AmadeusAccessTokenService accessTokenService;
	
	/**
	 * Sends a Hotel Offers request to Amadeus in order to retrieve all the available
	 * offers for the given criteria and constructs the {@code GetHotelOffersResponse}.
	 * 
	 * @param hotelOffersRequest the {@code GetHotelOffersRequest} which contains the
	 * 							 criteria that will be used to search for offers
	 * @return the constructed {@code GetHotelOffersResponse}
	 */
	public GetHotelOffersResponse getHotelOffers(GetHotelOffersRequest hotelOffersRequest) {
		String accessToken = accessTokenService.getAccessToken();
		
		WebClient client = WebClient.create(buildAmadeusHotelOffersUrl(hotelOffersRequest));
		String response = client.get()
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
								.retrieve()
								.bodyToMono(String.class)
								.block();
		
		return buildHotelOffersResponse(response);
	}
	
	/**
	 * Uses the given {@code GetHotelOffersRequest} in order to create the URL that
	 * will be used in order to get the available hotel offers from Amadeus.
	 * 
	 * @param hotelOffersRequest the {@code GetHotelOffersRequest} which contains the
	 * 							 criteria that will be used to search for offers
	 * @return the constructed Amadeus Hotel Offers URL
	 */
	private String buildAmadeusHotelOffersUrl(GetHotelOffersRequest hotelOffersRequest) {
		StringBuilder amadeusHotelOffersUrlBuilder = new StringBuilder();
		
		amadeusHotelOffersUrlBuilder.append("https://test.api.amadeus.com/v3/shopping/hotel-offers?");
		amadeusHotelOffersUrlBuilder.append("hotelIds=");
		amadeusHotelOffersUrlBuilder.append(hotelOffersRequest.getHotelId());
		amadeusHotelOffersUrlBuilder.append("&checkInDate=");
		amadeusHotelOffersUrlBuilder.append(hotelOffersRequest.getCheckInDate());
		amadeusHotelOffersUrlBuilder.append("&checkOutDate=");
		amadeusHotelOffersUrlBuilder.append(hotelOffersRequest.getCheckOutDate());
		amadeusHotelOffersUrlBuilder.append("&adults=");
		amadeusHotelOffersUrlBuilder.append(hotelOffersRequest.getAdults());
		
		return amadeusHotelOffersUrlBuilder.toString();
	}
	
	/**
	 * Loads the given response to a {@code JsonNode} and then parses it in order to extract
	 * the needed fields to build the {@code GetHotelOffersResponse}.
	 * 
	 * @param jsonResponse the response to the Amadeus Hotel Offers request
	 * @return the constructed {@code GetHotelOffersResponse}
	 */
	public GetHotelOffersResponse buildHotelOffersResponse(String jsonResponse) {
		try {
			GetHotelOffersResponse getHotelOffersResponse = new GetHotelOffersResponse();
			JsonNode hotelDetails = new ObjectMapper().readTree(jsonResponse).get("data").get(0);
			
			getHotelOffersResponse.setHotelName(hotelDetails.get("hotel").get("name").asText());
			
			hotelDetails.get("offers").forEach( offer -> {
				HotelOffer hotelOffer = new HotelOffer();
				hotelOffer.setId(offer.get("id").asText());
				hotelOffer.setCheckInDate(offer.get("checkInDate").asText());
				hotelOffer.setCheckOutDate(offer.get("checkOutDate").asText());
				JsonNode guests = offer.get("guests");
				hotelOffer.setAdults(guests.get("adults").asInt());
				if(guests.has("children")) {
					hotelOffer.setChildren(guests.get("children").asInt());
				}
				
				if(guests.has("infants")) {
					hotelOffer.setInfants(guests.get("infants").asInt());
				}
				
				JsonNode room = offer.get("room");
				HotelOfferRoom hotelRoom = new HotelOfferRoom();
				JsonNode typeEstimated = room.get("typeEstimated");
				if(typeEstimated.has("beds")) {
					hotelRoom.setNumberOfBeds(typeEstimated.get("beds").asInt());
				}
				if(typeEstimated.has("bedType")) {
					hotelRoom.setBedType(typeEstimated.get("bedType").asText());
				}
				hotelRoom.setRoomDescription(room.get("description").get("text").asText());
				
				JsonNode price = offer.get("price");
				HotelOfferPrice offerPrice = new HotelOfferPrice();
				offerPrice.setCurrency(price.get("currency").asText());
				offerPrice.setTotalPrice(price.get("total").asText());
				price.get("taxes").forEach( tax -> {
					HotelOfferTaxItem taxItem = new HotelOfferTaxItem();
					taxItem.setDescription(tax.get("code").asText());
					
					if(tax.has("amount")) {
						taxItem.setAmount(tax.get("amount").asText());
					} else {
						BigDecimal multiplier = new BigDecimal(tax.get("percentage").asText()).divide(BigDecimal.TEN).divide(BigDecimal.TEN);
						BigDecimal amount = new BigDecimal(offerPrice.getTotalPrice());
						
						taxItem.setAmount(amount.multiply(multiplier).setScale(2, RoundingMode.HALF_UP).toPlainString());
					}
					
					offerPrice.addTax(taxItem);
				});
				
				hotelOffer.setPrice(offerPrice);
				hotelOffer.setRoom(hotelRoom);
				getHotelOffersResponse.addOffer(hotelOffer);
			});
			
			return getHotelOffersResponse;
		} catch (JsonProcessingException e) {
			//TODO Add a proper error message
			e.printStackTrace();
		}
		
		return null;
	}

}
