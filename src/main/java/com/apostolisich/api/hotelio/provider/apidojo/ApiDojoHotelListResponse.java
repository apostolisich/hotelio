package com.apostolisich.api.hotelio.provider.apidojo;

import java.util.List;

public class ApiDojoHotelListResponse {
	
	private List<ApiDojoHotelListResponseEntry> data;
	
	public List<ApiDojoHotelListResponseEntry> getData() {
		return data;
	}
	
	public void setData(List<ApiDojoHotelListResponseEntry> data) {
		this.data = data;
	}

}
