package com.cloudrip.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.User;
import com.cloudrip.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	user.getProvider(),user.getProvider_id()
//	,user.getNickname(),user.getPassword()
//	,user.getEmail(),user.getRoles()
//	,user.getUserRegdate(),user.getUsername())
	
	public void create(String provider,String provider_id,
			String nickname,String password,String email,
			String roles,String username) {
		User user= new User();
		user.setEmail(email);
		user.setProvider(provider);
		user.setProviderId(provider_id);
		user.setUserRegdate(LocalDate.now());
		user.setNickname(nickname);
		user.setRoles("ROLE_USER");
		user.setPassword(passwordEncoder.encode(password));
		user.setUsername(username);
		this.userRepository.save(user);
	}
	public List<User> getList(){
		return userRepository.findAll();
	}
	
	public User getUserNickname(String nickname){
		return userRepository.findByNickname(nickname);
	}
	
	public User findByProviderId(String providerId) {
		return userRepository.findByProviderId(providerId);
	}
	
	public User updateUserNickname(String nickname){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetails userDetails = (PrincipalDetails) principal;
		User user = userDetails.getUser();
		user.setNickname(nickname);
		this.userRepository.save(user);
		return user;
	}
	
	public void deleteUser(String providerId) {
		User user = userRepository.findByProviderId(providerId);
		if(user ==null) {
			System.out.println("찾고자 하는 유저가 없습니다.");
		}else {
			userRepository.delete(user);
		}
	}

}
