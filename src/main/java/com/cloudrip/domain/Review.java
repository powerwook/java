package com.cloudrip.domain;

import java.time.LocalDate;

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

import lombok.Data;

@Data
@Entity
@Table(name="review")
@IdClass(ReviewIds.class)
@Transactional
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reviewId")
	private Long reviewId;
	
	
	@Id
	@Column(name="boardId")
	private Long boardId;
	
	@ManyToOne
	@JoinColumn(name="boardId",referencedColumnName = "boardId",insertable=false, updatable=false)
	private Board board;
	
	@ManyToOne
	@JoinColumn(name="nickname")
	private User user;
	
	private String reviewContent;
	
	private LocalDate reviewRegdate;
	
	private Long reviewHit;
	
	@Column(nullable=false)
	private String reviewDebate;
	
}
