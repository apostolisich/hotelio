package com.apostolisich.api.hotelio.request;

/**
 * A class that represents the GetHotelList request which the user needs
 * to send in order to get all the available hotel entries based on the
 * given criteria.
 */
@SuppressWarnings("unused")
public final class GetHotelListRequest {
	
	private double latitude;
	private double longitude;
	private int radius;
	
	public double getLatitude() {
		return latitude;
	}
	
	private void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	private void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public int getRadius() {
		return radius;
	}
	
	private void setRadius(int radius) {
		this.radius = radius;
	}
	
}
