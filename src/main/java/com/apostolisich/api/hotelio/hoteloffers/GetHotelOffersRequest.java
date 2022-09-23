package com.apostolisich.api.hotelio.hoteloffers;

/**
 * A class that represents the hotel offers request which contains the
 * criteria provided by the user in order to get all the available hotel
 * offers for the specified hotel id.
 */
@SuppressWarnings("unused")
public class GetHotelOffersRequest {

	private String hotelId;
	private String provider;
	private String checkInDate;
	private String checkOutDate;
	private int adults;
	
	public GetHotelOffersRequest(String hotelId, String provider, String checkInDate, String checkOutDate, int adults) {
		this.hotelId = hotelId;
		this.provider = provider;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.adults = adults;
	}

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
