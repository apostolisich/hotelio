package com.apostolisich.api.hotelio.request;

import java.util.Objects;

/**
 * A class that represents the GetHotelList request which the user needs
 * to send in order to get all the available hotel entries based on the
 * given criteria.
 */
@SuppressWarnings("unused")
public final class GetHotelListRequest {
	
	private static final char KEY_DELIMITER = '@';
	
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
	
	public String getKey() {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(latitude);
		keyBuilder.append(KEY_DELIMITER);
		keyBuilder.append(longitude);
		keyBuilder.append(KEY_DELIMITER);
		keyBuilder.append(radius);
		
		return keyBuilder.toString();
	}
	
}
