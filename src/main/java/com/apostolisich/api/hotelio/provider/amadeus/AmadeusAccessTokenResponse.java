package com.apostolisich.api.hotelio.provider.amadeus;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AmadeusAccessTokenResponse {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	public String getAccessToken() {
		return accessToken;
	}
	
}
