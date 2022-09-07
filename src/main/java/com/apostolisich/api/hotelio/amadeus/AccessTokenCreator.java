package com.apostolisich.api.hotelio.amadeus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccessTokenCreator {
	
	@Value("${amadeus.api.key}")
	private String clientId;
	
	@Value("${amadeus.api.secret}")
	private String clientSecret;

	/**
	 * Sends a POST request providing the needed API key and secret in order to
	 * get an access token that needs to be used in the following HTTP requests.
	 * 
	 * @return an access token
	 */
	public AccessTokenResponse getAccessToken() {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(3);
		formData.add("client_id", clientId);
		formData.add("client_secret", clientSecret);
		formData.add("grant_type", "client_credentials");
		
		WebClient webClient = WebClient.create("https://test.api.amadeus.com/v1/security/oauth2/token");
		AccessTokenResponse response = webClient.post()
												.bodyValue(formData)
												.retrieve()
												.bodyToMono(AccessTokenResponse.class)
												.block();
		
		return response;
	}
	
}
