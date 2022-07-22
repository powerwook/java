package com.cloudrip.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.repository.ReviewRepository;
import com.cloudrip.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	user.getProvider(),user.getProvider_id()
//	,user.getNickname(),user.getPassword()
//	,user.getEmail(),user.getRoles()
//	,user.getUserRegdate(),user.getUsername())
	
//	public void create(String provider,String provider_id,
//			String nickname,String password,String email,
//			String roles,String username) {
//		User user= new User();
//		user.setEmail(email);
//		user.setProvider(provider);
//		user.setProviderId(provider_id);
//		user.setUserRegdate(LocalDate.now());
//		user.setNickname(nickname);
//		user.setRoles("ROLE_USER");
//		user.setPassword(passwordEncoder.encode(password));
//		user.setUsername(username);
//		this.userRepository.save(user);
//	}

	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	
	public List<User> findAllEmailSort(){
		List<User> userEmailSortList = userRepository.findAll(Sort.by(Sort.Direction.ASC,"email"));
		return userEmailSortList;
	}
	
	public List<User> getList(){
		return userRepository.findAll();
	}
	
	public User findByNickname(String nickname){
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
	      List<Review> UserReviewList =user.getReviews();
//	         사용자가 쓴 리뷰가 있다면 그 리뷰들을 먼저 삭제해줘야 함. 
	      if(UserReviewList!=null) {
	         for (Review review : UserReviewList) {
	            reviewRepository.delete(review);
	         }
	      }
	      userRepository.delete(user);
	   }
	
	   public Page<User> findByEmailContainingOrNicknameContainingOrRolesContaining(Pageable pageable, String keyword1,  String keyword2,  String keyword3) {
		      return userRepository.findByEmailContainingOrNicknameContainingOrRolesContaining(pageable, keyword1, keyword2, keyword3);
		   }
		   
	   public Page<User> findAll(Pageable pageable) {
	      return userRepository.findAll(pageable);
	   }
	   
	   public Page<User> findByEmailContaining(Pageable pageable, String keyword) {
	      return userRepository.findByEmailContaining(pageable, keyword);
	   }
	   
	   public Page<User> findByNicknameContaining(Pageable pageable, String keyword) {
	      return userRepository.findByNicknameContaining(pageable, keyword);
	   }
	   
	   public Page<User> findByRolesContaining(Pageable pageable, String keyword) {
	      return userRepository.findByRolesContaining(pageable, keyword);
	   }
	
	
	
	
//	
//	public Page<User> findByNicknameContaining(Pageable pageable,String nickname){
//		return userRepository.findByNicknameContaining(pageable, nickname);
//	}
//	
//	public Page<User> findByEmailContaining(String email){
//		Sort sort = Sort.by("email").descending();
//		Pageable pageable = PageRequest.of(0, 10,sort);
//		return userRepository.findByEmailContaining(pageable, email);
//	}
//	public Page<User> findByRegdateContaining(LocalDateTime userRegdate){
//		Sort sort = Sort.by("userRegdate").descending();
//		Pageable pageable = PageRequest.of(0, 10,sort);
//		return userRepository.findByUserRegdateContaining(pageable, userRegdate);
//	}
	
}
