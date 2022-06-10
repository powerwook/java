package com.cloudrip.config.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cloudrip.domain.User;

import lombok.Data;

@Data
public class PrincipalDetails implements OAuth2User{

	public Map<String, Object> attributes;
	
	@Autowired
	private User user;
	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				return user.getRoles();
			}
			
		});
		return collect;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

}
