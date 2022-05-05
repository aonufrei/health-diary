package com.aonufrei.healthdiary.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;

	private final ObjectMapper mapper;
	private final Logger LOG = LoggerFactory.getLogger(JwtUsernameAndPasswordAuthorizationFilter.class);

	public JwtUtils() {
		this.mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
	}

	public <T> Optional<String> encode(T object, LocalDateTime expiration) {
		return Optional.ofNullable(object).map(s -> Jwts.builder()
				.setSubject(object.toString())
				.setExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact());
	}

	public <T> Optional<String> convertToJsonString(T object) {
		try {
			return Optional.ofNullable(mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			LOG.error("Cannot serialize object to json", e);
			return Optional.empty();
		}
	}

	public <T> Optional<T> convertToJsonObject(String stringObject, Class<T> clazz) {
		try {
			return Optional.ofNullable(mapper.readValue(stringObject, clazz));
		} catch (JsonProcessingException e) {
			LOG.error("Cannot serialize object to json", e);
			return Optional.empty();
		}
	}

	public Optional<String> decode(String token) {
		try {
			String decodedString = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
			return Optional.ofNullable(decodedString);
		} catch (ExpiredJwtException expEx) {
			LOG.error("Token expired", expEx);
		} catch (UnsupportedJwtException unsEx) {
			LOG.error("Unsupported jwt", unsEx);
		} catch (MalformedJwtException mjEx) {
			LOG.error("Malformed jwt", mjEx);
		} catch (SignatureException sEx) {
			LOG.error("Invalid signature", sEx);
		} catch (Exception e) {
			LOG.error("invalid token", e);
		}

		return Optional.empty();
	}
}
