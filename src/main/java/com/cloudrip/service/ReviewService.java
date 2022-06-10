package com.cloudrip.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Review;
import com.cloudrip.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	public void create(Long board_id,Long review_id,
			String review_content,String review_debate,Long review_hit,
			String nickname) {
		Review review = new Review();
		review.setBoardId(board_id);	
		review.setReviewId(review_id);	
		review.setReviewContent(review_content);	
		review.setReviewDebate(review_debate);	
		review.setReviewHit(review_hit);	
		review.setReviewRegdate(LocalDate.now());
		reviewRepository.save(review);
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
