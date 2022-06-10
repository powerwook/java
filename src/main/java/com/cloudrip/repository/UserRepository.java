package com.cloudrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudrip.domain.User;

public interface UserRepository extends JpaRepository<User, String>{
	public User findByNickname(String nickname);
	public User findByUsername(String username);
	
	public User findByProviderId(String providerId);
}
