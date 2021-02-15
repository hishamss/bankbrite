package net.revature.bankbrite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.revature.bankbrite.dto.LoginForm;
import net.revature.bankbrite.dto.LoginResponse;
import net.revature.bankbrite.service.MyUserDetailsService;
import net.revature.bankbrite.util.JwtUtil;

@RestController
@RequestMapping("/signin")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping
	public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
			final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginForm.getUsername());
			final String jwt = jwtUtil.createToken(userDetails);
			return new ResponseEntity<>(new LoginResponse(jwt), HttpStatus.OK);

		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

	}

}
