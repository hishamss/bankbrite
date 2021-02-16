package net.revature.bankbrite.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import net.revature.bankbrite.service.MyUserDetailsService;
import net.revature.bankbrite.util.JwtUtil;

@Component
public class JwtEveryRequestFilter extends OncePerRequestFilter {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authorizationHeader = request.getHeader("Authorization");
		String jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			Jws<Claims> claims = jwtUtil.parseToken(jwt);
			final String username = claims.getBody().get("username").toString();
			// since we configured spring security in SecurityConfigurer to be statless; which means that spring will not save any info
			// about the current user; so every request the securityContextHolder will be null
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}
		filterChain.doFilter(request, response);
	}

}
