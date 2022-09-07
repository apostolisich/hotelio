package com.apostolisich.api.hotelio.amadeus;

public final class AccessTokenResponse {
	
	private String type;
	private String username;
	private String application_name;
	private String client_id;
	private String token_type;
	private String access_token;
	private String expires_in;
	private String state;
	private String scope;
	
	public AccessTokenResponse() { 
		
	}

	public AccessTokenResponse(String type, String username, String application_name, String client_id,
			String token_type, String access_token, String expires_in, String state, String scope) {
		this.type = type;
		this.username = username;
		this.application_name = application_name;
		this.client_id = client_id;
		this.token_type = token_type;
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.state = state;
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}

	public String getApplication_name() {
		return application_name;
	}

	public String getClient_id() {
		return client_id;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getAccess_token() {
		return access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public String getState() {
		return state;
	}

	public String getScope() {
		return scope;
	}
	
}
