package com.aonufrei.healthdiary.security.jwt;

import com.aonufrei.healthdiary.dtos.AuthorizationCredentials;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtUsernameAndPasswordAuthorizationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;

	public JwtUsernameAndPasswordAuthorizationFilter(UserDetailsService userDetailsService, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.userDetailsService = userDetailsService;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String bearer = request.getHeader("Authorization");

		getToken(bearer)
				.flatMap(jwtUtils::decode)
				.flatMap(it -> jwtUtils.convertToJsonObject(it, AuthorizationCredentials.class))
				.ifPresent(credentials -> authorize(credentials, request));

		filterChain.doFilter(request, response);
	}

	private void authorize(AuthorizationCredentials credentials, HttpServletRequest request) {
		if (credentials.getUsername() == null || credentials.getPassword() == null) return;
		UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getUsername());
		if (userDetails == null || !encoder.matches(credentials.getPassword(), userDetails.getPassword())) return;
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities()
		);
		auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private Optional<String> getToken(String auth) {
		return Optional.ofNullable(auth).map(it -> it.substring(7));
	}

}
