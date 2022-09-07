package com.apostolisich.api.hotelio.amadeus;

public final class AmadeusAccessTokenResponse {
	
	private String access_token;
	
	public AmadeusAccessTokenResponse() { 
		
	}
	
	public String getAccessToken() {
		return access_token;
	}
	
	/**
	 * A setter method that is used in order to automatically set the "access_token" from the
	 * token generation JSON response.
	 * 
	 * @param access_token the access token in the JSON response
	 */
	@SuppressWarnings("unused")
	private void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
