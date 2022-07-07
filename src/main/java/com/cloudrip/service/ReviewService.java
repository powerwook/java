package com.cloudrip.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.repository.BoardRepository;
import com.cloudrip.repository.ReviewRepository;
import com.cloudrip.repository.UserRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	public void reviewInsert(String reviewContent,String reviewDebate,String nickname,Board board) {
		User user = userRepository.findByNickname(nickname);
		LocalDateTime now = LocalDateTime.now();
		String reviewTime = now.getHour() + ":" + now.getMinute();
		
		Review review = new Review();
		review.setBoard(board);
		review.setUser(user);
		review.setReviewContent(reviewContent);
		review.setReviewDebate(reviewDebate);
		review.setReviewHit(0l);
		review.setReviewEli(0l);
		review.setReviewFlt(0l);
		System.out.println(LocalDateTime.now());
		review.setReviewRegdate(LocalDateTime.now());
		review.setReviewTime(reviewTime);
		reviewRepository.save(review);
		
	}
	

	public List<Review> findAll() {
		List<Review> list = reviewRepository.findAll();
		return list;
	}
	
	@Transactional
	public void create(Board board) {
		Review review = new Review();
		System.out.println("board객체:"+board);
		review.setReviewContent("review입니다");
		review.setReviewHit(0l);
		review.setReviewRegdate(LocalDateTime.now());
		review.setReviewDebate("찬성");
		review.setBoard(board);
		
		
		reviewRepository.saveAndFlush(review);
		}
	
	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findByReviewId(reviewId);
		reviewRepository.delete(review);
		System.out.println("나는 리뷰닷"+review);
	}
	
	public Page<Review> findByReviewContentContaining(Pageable pageable,String reviewContent){
		return reviewRepository.findByReviewContentContaining(pageable, reviewContent);
	}
	
	public void deleteReviewAll(PrincipalDetails principalDetails) {
		User user = principalDetails.getUser();
		User findUser =  userRepository.findByProviderId(user.getProviderId());
		for (Review review : findUser.getReviews()) {
			reviewRepository.delete(review);
		}
	}
	
	public void updateFilter(Long id,Long filter) {
		Review review = reviewRepository.findByReviewId(id);
		review.setReviewFlt(filter);
		reviewRepository.save(review);
	}
	
	
}
