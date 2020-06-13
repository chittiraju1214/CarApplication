package com.example.car.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.example.car.exception.TokenException;
import com.example.car.service.JwtUserDetailService;
import com.example.car.util.JwtUtil;

import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private JwtUserDetailService jwtUserDetailService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		String url = request.getRequestURL().toString();
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && !url.contains("logout")) {
			jwtToken = authorizationHeader.substring(7);
			try {
				username = jwtUtil.getUserIdFromToken(jwtToken);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
				throw new TokenException("Authentication Failed. Username or Password not valid.", e);
			} catch (Exception ex) {
				logger.error("Invalid JWT token");
				throw new TokenException("Invalid JWT token", ex);
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
			try {
				if (userDetails != null) {
					if (jwtUtil.validateToken(jwtToken, userDetails)) {
						final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					} else {
						throw new TokenException("Token expired ");
					}
				} else {
					throw new TokenException("Authentication Failed. Username or Password is not valid.");
				}
			} catch (TokenException e) {
				throw new TokenException("Invalid JWT token", e);
			}
		}
		filterChain.doFilter(request, response);
	}
}
