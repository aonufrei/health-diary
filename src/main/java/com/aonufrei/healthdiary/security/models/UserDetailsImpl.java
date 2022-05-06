package com.aonufrei.healthdiary.security.models;

import com.aonufrei.healthdiary.models.Credentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private Integer id;
	private String username;
	private String password;
	private Integer personId;
	private String role;


	public UserDetailsImpl(Credentials credentials) {
		this.id = credentials.getId();
		this.username = credentials.getUsername();
		this.password = credentials.getPassword();
		this.personId = credentials.getPersonId();
		this.role = credentials.getAuthority().name();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>() {{
			add(new SimpleGrantedAuthority(role));
		}};
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}