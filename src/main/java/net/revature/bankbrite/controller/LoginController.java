package net.revature.bankbrite.controller;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.revature.bankbrite.dto.LoginForm;

@RestController
@RequestMapping("/signin")
public class LoginController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<LoginForm> login(@RequestBody LoginForm loginForm) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
			if(authentication.isAuthenticated()) {
				return new ResponseEntity<>(loginForm, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(loginForm, HttpStatus.UNAUTHORIZED);
			}
			
		}catch(BadCredentialsException e) {
			return new ResponseEntity<>(loginForm, HttpStatus.UNAUTHORIZED);
		}
		
		
	}

}
