package com.app.polling.payloads;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotBlank
	private String userNameOrEmail;
	
	@NotBlank
	private String password;
	
}
