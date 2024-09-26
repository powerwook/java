//package com.cloudrip.dto;
//
//import com.cloudrip.domain.Review;
//import com.cloudrip.domain.User;
//
//import lombok.Data;
//@Data
//public class ReviewUploadDto {
//	private Long boardId;
//	private String reviewContent;
//	private String reviewDebate;
//	private Long reviewHit;
//	private String nickname;
//	
//	public Review toEntity(Long boardId,String reviewContent,
//			String reviewDebate, Long reviewHit) {
//				return Review.builder()
//						.boardId(boardId)
//						.reviewContent(reviewContent)
//						.reviewDebate(reviewDebate)
//						.reviewHit(reviewHit)
//						.build();
//	}
//}
