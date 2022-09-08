package com.apostolisich.api.hotelio.response;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents a GetHotelListResponse. It includes all the hotel
 * entries that were returned by one of the providers.
 */
public final class GetHotelListResponse {

	private String providerName;
	private Set<HotelEntry> entries;

	public GetHotelListResponse(String providerName) {
		this.providerName = providerName;
		entries = new HashSet<>();
	}
	
	public String getProviderName() {
		return providerName;
	}
	
	public Set<HotelEntry> getEntries() {
		return entries;
	}
	
	public void addHotelEntry(String id, String iataCode, String name, double latitude, double longitude) {
		entries.add(new HotelEntry(id, iataCode, name, latitude, longitude));
	}
	
	@SuppressWarnings("unused")
	private class HotelEntry {
		private String id;
		private String iataCode;
		private String name;
		private double latitude;
		private double longitude;
		
		public HotelEntry(String id, String iataCode, String name, double latitude, double longitude) {
			this.id = id;
			this.iataCode = iataCode;
			this.name = name;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public String getId() {
			return id;
		}

		public String getIataCode() {
			return iataCode;
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
	
}
