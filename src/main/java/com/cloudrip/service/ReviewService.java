package com.cloudrip.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cloudrip.config.auth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.repositories.BoardRepository;
import com.cloudrip.repositories.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	public void reviewInsert(String reviewContent,String reviewDebate,String nickname,Board board) {
		
		LocalDateTime now = LocalDateTime.now();
		String reviewTime = now.getHour() + ":" + now.getMinute();
		
		Review review = new Review();
		review.setBoard(board);
		review.setNickname(nickname);
		review.setReviewContent(reviewContent);
		review.setReviewDebate(reviewDebate);
		review.setReviewHit(0l);
		System.out.println(LocalDateTime.now());
		review.setReviewRegdate(LocalDateTime.now());
		review.setReviewTime(reviewTime);
		
		reviewRepository.save(review);
		
	}
	
//	public Review reviewInsert(Map<String, Object> review, Board board) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String nickname = authentication.getName();
//		LocalDateTime now = LocalDateTime.now();
//		String reviewTime = now.getHour() + ":" + now.getMinute();
////		Review reviewEntity = new Review();
////		reviewEntity.setBoardId(review.getBoardId());
////		reviewEntity.setReviewContent(review.getReviewContent());
////		reviewEntity.setReviewDebate(review.getReviewDebate());
////		reviewEntity.setReviewRegdate(LocalDate.now());
////		reviewEntity.setNickname(nickname);
////		reviewEntity.setReviewHit(0l);
////		reviewRepository.save(reviewEntity);
//		
//		String reviewContent =(String)review.get("reviewContent");
//		String reviewDebate =(String)review.get("reviewDebate");
//		
//		Review reviewEntity = Review.builder()
//			.board(board)
//			.reviewContent(reviewContent)
//			.reviewDebate(reviewDebate)
//			.reviewRegdate(now)
//			.nickname(nickname)
//			.reviewHit(0l)
//			.reviewTime(reviewTime)
//			.build();
//		System.out.println("reviewEntity : " + reviewEntity);
//		reviewRepository.save(reviewEntity);
//		
//		return reviewEntity;
//	}
	
	public List<Review> findAll() {
		List<Review> list = reviewRepository.findAll();
		return list;
	}
	
	
//	public List<Review> findByBoardId(Long boardId) {
//		Board board = boardRepository.findByBoardId(boardId);
//		List<Review> reviewList = reviewRepository.findByBoard(board); 
//		return reviewList;
//	}
	
	
}
