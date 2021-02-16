package net.revature.bankbrite.util;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@Service
public class JwtUtil {

	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	private static final SecretKey SECRET_KEY = MacProvider.generateKey(SIGNATURE_ALGORITHM);
	private static final TemporalAmount TOKEN_VALIDITY = Duration.ofHours(4L);

	public String createToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
		claims.put("role", authorities.get(0).getAuthority());
		claims.put("email", authorities.get(1).getAuthority());
		claims.put("username", authorities.get(2).getAuthority());
		final Instant now = Instant.now();
		final Date expiryDate = Date.from(now.plus(TOKEN_VALIDITY));
		return Jwts.builder().setClaims(claims).setExpiration(expiryDate).setIssuedAt(Date.from(now))
				.signWith(SIGNATURE_ALGORITHM, SECRET_KEY).compact();
	}

	public Jws<Claims> parseToken(final String jwtToken) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException {

		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
	}

}
