package com.apostolisich.api.hotelio.hoteloffers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A class the represents the entire response of the Hotel Offers response.
 * It includes all the offers that are available for the selected offer
 * criteria.
 */
@SuppressWarnings("unused")
public class GetHotelOffersResponse {
	
	private String hotelName;
	private List<HotelOffer> offers;
	
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	public List<HotelOffer> getOffers() {
		return offers;
	}

	public void addOffer(HotelOffer offer) {
		if(offers == null) {
			offers = new ArrayList<>();
		}
		
		offers.add(offer);
	}
	
	public static class HotelOffer {
		
		private String id;
		private String checkInDate;
		private String checkOutDate;
		private int adults;
		@JsonInclude(Include.NON_NULL)
		private int children;
		@JsonInclude(Include.NON_NULL)
		private int infants;
		private HotelOfferRoom room;
		private HotelOfferPrice price;
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getCheckInDate() {
			return checkInDate;
		}
		
		public void setCheckInDate(String checkInDate) {
			this.checkInDate = checkInDate;
		}
		
		public String getCheckOutDate() {
			return checkOutDate;
		}
		
		public void setCheckOutDate(String checkOutDate) {
			this.checkOutDate = checkOutDate;
		}
		
		public int getAdults() {
			return adults;
		}
		
		public void setAdults(int adults) {
			this.adults = adults;
		}
		
		public int getChildren() {
			return children;
		}
		
		public void setChildren(int children) {
			this.children = children;
		}
		
		public int getInfants() {
			return infants;
		}
		
		public void setInfants(int infants) {
			this.infants = infants;
		}
		
		public HotelOfferRoom getRoom() {
			return room;
		}

		public void setRoom(HotelOfferRoom room) {
			this.room = room;
		}

		public HotelOfferPrice getPrice() {
			return price;
		}

		public void setPrice(HotelOfferPrice price) {
			this.price = price;
		}
		
	}
	
	public static class HotelOfferRoom {
		
		@JsonInclude(Include.NON_DEFAULT)
		private int numberOfBeds;
		@JsonInclude(Include.NON_NULL)
		private String bedType;
		private String roomDescription;
		
		public int getNumberOfBeds() {
			return numberOfBeds;
		}
		
		public void setNumberOfBeds(int numberOfBeds) {
			this.numberOfBeds = numberOfBeds;
		}
		
		public String getBedType() {
			return bedType;
		}
		
		public void setBedType(String bedType) {
			this.bedType = bedType;
		}
		
		public String getRoomDescription() {
			return roomDescription;
		}
		
		public void setRoomDescription(String roomDescription) {
			this.roomDescription = roomDescription;
		}
		
	}
	
	public static class HotelOfferPrice {
		
		private String currency;
		private String totalPrice;
		private List<HotelOfferTaxItem> taxes;
		
		public String getCurrency() {
			return currency;
		}
		
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		
		public String getTotalPrice() {
			return totalPrice;
		}
		
		public void setTotalPrice(String totalPrice) {
			this.totalPrice = totalPrice;
		}
		
		public List<HotelOfferTaxItem> getTaxes() {
			return taxes;
		}
		
		public void addTax(HotelOfferTaxItem tax) {
			if(this.taxes == null) {
				this.taxes = new ArrayList<>();
			}
			
			this.taxes.add(tax);
		}
		
	}
	
	public static class HotelOfferTaxItem {
		
		private String description;
		private String amount;
		
		public String getDescription() {
			return description;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getAmount() {
			return amount;
		}
		
		public void setAmount(String amount) {
			this.amount = amount;
		}
		
	}

}
