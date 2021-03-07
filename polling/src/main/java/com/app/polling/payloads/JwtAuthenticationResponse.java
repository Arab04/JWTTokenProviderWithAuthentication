package com.app.polling.payloads;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	private String accessToken;
	private String tokenType = "Bearer";
	
}
