package com.apostolisich.api.hotelio.amadeus;

/**
 * A class that represents each entry inside the Amadeus Hotel List.
 */
@SuppressWarnings("unused")
public final class AmadeusHotelListResponseEntry {

	private String hotelId;
	private String iataCode;
	private String name;
	private GeoCode geoCode;
	
	public String getHotelId() {
		return hotelId;
	}

	private void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getIataCode() {
		return iataCode;
	}

	private void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public GeoCode getGeoCode() {
		return geoCode;
	}

	private void setGeoCode(GeoCode geoCode) {
		this.geoCode = geoCode;
	}

	/**
	 * An inner class that represents geoCode objects which consist of the
	 * latitude and the longtitude of the hotel.
	 */
	private class GeoCode {
		
		private double latitude;
		private double longitude;
		
		public GeoCode() {
			
		}
		
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
		
	}
	
}
