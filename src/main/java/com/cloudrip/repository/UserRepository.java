package com.cloudrip.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;

public interface UserRepository extends JpaRepository<User, String>{
	public User findByNickname(String nickname);
	public User findByUsername(String username);
	
	public User findByProviderId(String providerId);
	
	  public Page<User> findAll(Pageable pageable);
	   
	   public Page<User> findByEmailContaining(Pageable pageable, String keyword);
	   
	   public Page<User> findByNicknameContaining(Pageable pageable, String keyword);
	   
	   public Page<User> findByRolesContaining(Pageable pageable, String keyword);
	   
	   public Page<User> findByEmailContainingOrNicknameContainingOrRolesContaining(Pageable pageable, String keyword1, String keyword2, String keyword3);
}
