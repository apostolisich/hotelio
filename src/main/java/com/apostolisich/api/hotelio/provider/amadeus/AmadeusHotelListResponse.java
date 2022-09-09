package com.apostolisich.api.hotelio.provider.amadeus;

import java.util.List;

/**
 * A class the represents the entire response of the Amadeus Hotel List.
 */
public final class AmadeusHotelListResponse {
	
	private List<AmadeusHotelListResponseEntry> data;
	
	public List<AmadeusHotelListResponseEntry> getData() {
		return data;
	}

	public void setData(List<AmadeusHotelListResponseEntry> data) {
		this.data = data;
	}
	
}
