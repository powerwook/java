package com.cloudrip.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="review")
//@IdClass(ReviewIds.class)
@Transactional
@NoArgsConstructor
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="review_id")
	private Long reviewId;
	
	
//	@Id
//	@Column(name="board_id")
//	private Long boardId;
	
	@ManyToOne
	@JoinColumn(name="board_id")
	private Board board;
	
	@ManyToOne
	@JoinColumn(name="nickname")
	private User user;
	
	private String reviewContent;
	
	private LocalDateTime reviewRegdate;
	
	private Long reviewHit;
	
	@Column(nullable=false)
	private String reviewDebate;

	private String reviewTime;
	
	//삭제
	@Column(columnDefinition = "bigint default 0")
	private Long reviewEli;
	
	//필터
	@Column(columnDefinition = "bigint default 0")
	private Long reviewFlt;
}
