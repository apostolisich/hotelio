package com.apostolisich.api.hotelio.provider.apidojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiDojoHotelListResponseEntry {

	@JsonProperty("location_id")
	private String hotelId;
	private String name;
	private double latitude;
	private double longitude;
	
	public ApiDojoHotelListResponseEntry() {
		
	}
	
	public String getHotelId() {
		return hotelId;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
}
