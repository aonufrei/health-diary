package com.aonufrei.healthdiary.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

	@Mock
	private ObjectMapper objectMapper;

	@Spy
	@InjectMocks
	private JwtUtils jwtUtils;

	@Test
	void testEncodeAndDecode() {
		assertNotNull(jwtUtils);
		ReflectionTestUtils.setField(jwtUtils, "secret", "test-secretsecretsecret");
		String value = "text";
		String value2 = "text2";
		String token = jwtUtils.encode(value, LocalDateTime.now().plusMinutes(10)).orElse(null);
		assertNotNull(token);
		String token2 = jwtUtils.encode(value2, LocalDateTime.now().plusMinutes(10)).orElse(null);
		assertNotNull(token2);

		assertNotEquals(token, jwtUtils.encode(value, LocalDateTime.now().plusMinutes(10)).orElse(null));

		String decodedValue = jwtUtils.decode(token).orElse(null);
		assertNotNull(decodedValue);

		assertNull(jwtUtils.encode(null, LocalDateTime.now().plusMinutes(10)).orElse(null));
		assertNull(jwtUtils.decode("asdasdfasddf").orElse(null));
	}

	@Test
	void testDecode() {
	}
}