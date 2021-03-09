package com.app.polling.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.polling.exception.AppException;
import com.app.polling.model.Role;
import com.app.polling.model.RoleName;
import com.app.polling.model.User;
import com.app.polling.payloads.ApiResponse;
import com.app.polling.payloads.JwtAuthenticationResponse;
import com.app.polling.payloads.LoginRequest;
import com.app.polling.payloads.SignUpRequest;
import com.app.polling.reprository.RoleRepo;
import com.app.polling.reprository.UserRepo;
import com.app.polling.security.JWTTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	PasswordEncoder passEncoder;
	
	@Autowired
	JWTTokenProvider tokenProvider;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authenticate = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserNameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		String jwt = tokenProvider.generateToken(authenticate);
		
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	
	
	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUp) {
		if(userRepo.existsByUsername(signUp.getUsername())) {
			return new ResponseEntity(new ApiResponse("User with this user name already exist",false),HttpStatus.BAD_REQUEST);
		}
		
		else if(userRepo.existsByEmail(signUp.getEmail())) {
			return new ResponseEntity(new ApiResponse("User with this email already exists", false),HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(signUp.getName(), signUp.getUsername(), signUp.getEmail(), signUp.getPassword());
		
		user.setPassword(passEncoder.encode(user.getPassword()));
		
		Role userRole = roleRepo.findByName(RoleName.ROLE_USER)
				.orElseThrow(()-> new AppException("Role set should not be empty"));
		
		user.setRoles(Collections.singleton(userRole));
		
		User result = userRepo.save(user);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
		
		return ResponseEntity.created(location).body(new ApiResponse("User Successfully registered",true));
	}
}
































