package com.aonufrei.healthdiary.services;

import com.aonufrei.healthdiary.dtos.AuthorizationCredentials;
import com.aonufrei.healthdiary.dtos.CredentialsDto;
import com.aonufrei.healthdiary.dtos.CredentialsInDto;
import com.aonufrei.healthdiary.exceptions.DataValidationException;
import com.aonufrei.healthdiary.models.Credentials;
import com.aonufrei.healthdiary.repositories.CredentialsRepository;
import com.aonufrei.healthdiary.security.jwt.JwtUtils;
import com.aonufrei.healthdiary.security.models.UserDetailsImpl;
import com.aonufrei.healthdiary.utils.ModelDtoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CredentialsService extends AbstractCrudService<Integer, Credentials, CredentialsDto, CredentialsInDto, CredentialsRepository> implements UserDetailsService {

	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;

	private final Duration tokenExpirationDuration = Duration.ofDays(10);

	public CredentialsService(CredentialsRepository repo, Validator validator, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
		super(repo, ModelDtoUtil::inDtoToModel, ModelDtoUtil::modelToDto, ModelDtoUtil::updateModel);
		setValidator(validator);
		this.jwtUtils = jwtUtils;
		this.passwordEncoder = passwordEncoder;
	}

	public boolean authorize(HttpServletResponse response, AuthorizationCredentials credentials) {
		credentials.setCode(RandomStringUtils.randomAlphabetic(100));
		LocalDateTime endExpirationDate = LocalDateTime.now().plus(tokenExpirationDuration);
		Optional<String> token = jwtUtils.convertToJsonString(credentials).flatMap(it -> jwtUtils.encode(it, endExpirationDate));
		UserDetails userDetails = loadUserByUsername(credentials.getUsername());
		if (userDetails != null && passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword()) && token.isPresent()) {
			response.setHeader("Authorization", "Bearer " + token.get());
			return true;
		}
		return false;
	}

	@Transactional
	public boolean register(CredentialsInDto credentials) {
		if (credentials == null) return false;
		validator.validate(credentials).stream().findFirst().ifPresent(it -> {
			throw new DataValidationException(it.getMessage());
		});
		if (repo.existsCredentialsByUsername(credentials.getUsername())) return false;
		if (repo.existsCredentialsByUsernameAndPassword(credentials.getUsername(), credentials.getPassword())) return false;
		credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
		repo.save(inDtoToModelFunction.apply(credentials));
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(username)
				.flatMap(it -> Optional.ofNullable(repo.getFirstByUsername(username)))
				.map(UserDetailsImpl::new)
				.orElse(null);
	}
}
