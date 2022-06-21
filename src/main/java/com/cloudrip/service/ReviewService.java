package com.cloudrip.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.repository.ReviewRepository;

import lombok.NoArgsConstructor;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Transactional
	public void create(Board board) {
		Review review = new Review();
		System.out.println("board객체:"+board);
		review.setReviewContent("review입니다");
		review.setReviewHit(0l);
		review.setReviewRegdate(LocalDate.now());
		review.setReviewDebate("찬성");
		review.setBoard(board);
		
		
		reviewRepository.saveAndFlush(review);
		}
	
	public List<Review> findAllReview() {
		return reviewRepository.findAll();
	}
	
	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findByReviewId(reviewId);
		if(review==null) {
			System.out.println("삭제하고자 하는 리뷰id 가 없습니다.");
		}else {
			reviewRepository.delete(review);
		}
	}
}
