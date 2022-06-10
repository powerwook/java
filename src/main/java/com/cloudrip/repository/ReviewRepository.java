package com.cloudrip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cloudrip.domain.Review;


public interface ReviewRepository extends JpaRepository<Review, Long>{
	public Review findByReviewId(Long reviewId);
}
