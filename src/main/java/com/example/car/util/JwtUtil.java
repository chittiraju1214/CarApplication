package com.example.car.util;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.example.car.constants.AppProperties;
import com.example.car.constants.PropertyType;
import com.example.car.exception.TokenException;
import com.example.car.model.TokenPair;
import com.example.car.model.UserToken;
import com.example.car.model.Users;
import com.example.car.repository.JwtTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	@Autowired
	Environment environment;

	@Autowired
	JwtTokenRepository jwtTokenRepository;

	public String generateToken(Users user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("orgId", user);
		return doGenerateToken(claims, user.getUserId() + "");
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS512,
						environment.getProperty(AppProperties.JWT_TOKEN_SECRET_KEY.getPropName()))
				.compact();
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(environment.getProperty(AppProperties.JWT_TOKEN_SECRET_KEY.getPropName()))
				.parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws TokenException {
		final String userId = getUserIdFromToken(token);
		return (userId.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public TokenPair createRefreshAndAccessToken(Users user) {
		String refreshToken = RandomStringUtils.randomAlphanumeric(128);
		UserToken userToken = new UserToken();
		userToken.setTokenType(PropertyType.REFRESH_TOKEN.getTokenTypeId());
		userToken.setToken(refreshToken);
		userToken.setUser(user);
		String accessToken = generateToken(user);
		UserToken userAccessToken = new UserToken();
		userAccessToken.setTokenType(PropertyType.AUTHORIZATION_TOKEN.getTokenTypeId());
		userAccessToken.setToken(accessToken);
		userAccessToken.setUser(user);
		jwtTokenRepository.saveAll(Arrays.asList(userAccessToken, userToken));
		return new TokenPair(accessToken, refreshToken);
	}

}
