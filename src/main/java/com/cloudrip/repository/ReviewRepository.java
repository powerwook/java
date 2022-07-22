package com.cloudrip.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	List<Review> findAll();
	Review findByReviewId(Long reviewId);
	List<Review> findByBoard(Board Board);
	
	Page<Review> findByReviewContentContaining(Pageable pageable,String reviewContent);
	
	@Query(value="SELECT * from review where review_flt=0",nativeQuery = true)
	Page<Review> findByReviewNoFltContaining(Pageable pageable);
	
	@Query(value="SELECT * from review where review_flt=1",nativeQuery = true)
	Page<Review> findByReviewFltContaining(Pageable pageable);
	
	  @Query(nativeQuery=true, 
	            value="SELECT * from review where timestampdiff(second,review_regdate,now())<60")
	List<Review> findByReviewOneMinute();
	
	List<Review> findTop5ByBoardOrderByReviewHitDesc(Board board);
	  
}
