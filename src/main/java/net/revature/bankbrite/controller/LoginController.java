package net.revature.bankbrite.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.MalformedJwtException;
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
	public ResponseEntity<LoginResponse> login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
		try {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					loginForm.getUsername(), loginForm.getPassword());
			authenticationManager.authenticate(authentication);

			final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginForm.getUsername());
			final String jwt = jwtUtil.createToken(userDetails);
			List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
			return new ResponseEntity<>(new LoginResponse(jwt, authorities.get(1).getAuthority() ,authorities.get(0).getAuthority()), HttpStatus.OK);

		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

}
