package com.apostolisich.api.hotelio.hoteloffers;

/**
 * A class that represents the GetHotelOffers request which the user needs
 * to send in order to get all the available hotel offers based on the
 * given criteria.
 */
@SuppressWarnings("unused")
public class GetHotelOffersRequest {

	private String hotelId;
	private String provider;
	private String checkInDate;
	private String checkOutDate;
	private int adults;
	
	public String getHotelId() {
		return hotelId;
	}
	
	private void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	public String getProvider() {
		return provider;
	}
	
	private void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getCheckInDate() {
		return checkInDate;
	}
	
	private void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	public String getCheckOutDate() {
		return checkOutDate;
	}
	
	private void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public int getAdults() {
		return adults;
	}
	
	private void setAdults(int adults) {
		this.adults = adults;
	}
	
}
