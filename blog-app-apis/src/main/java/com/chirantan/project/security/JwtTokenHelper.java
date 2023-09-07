package com.chirantan.project.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.chirantan.project.config.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	public long JWT_TOKEN_VALIDITY = AppConstants.TOKEN_VALIDITY;

	private String secret = "jwtTokenKey";

	// retrieve username from JWT token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from JWT token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	// retrieve Claims from token
		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims= getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
		
		// retrieve any info from token we will need a secret key
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}
		
		//check if token is expired
		private Boolean isTokenExpired(String token) {
			final Date expiration= getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
		//generate token for User
		public String generateToken(UserDetails userDetails) {
			Map<String, Object> claims = new HashMap<>();
			return doGenerateToken(claims,userDetails.getUsername());
		}
		private String doGenerateToken(Map<String, Object> claims, String subject) {
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*100)).signWith(SignatureAlgorithm.HS512, secret).compact();
		}
		
		//validate token
		public Boolean validateToken(String token, UserDetails userDetails) {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername() )&& !isTokenExpired(token));
		}


}
