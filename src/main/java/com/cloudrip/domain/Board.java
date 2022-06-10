package com.cloudrip.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.Data;

@Data
@Entity
@Table(name="board")
@Transactional
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long boardId;
	
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="board")
	private List<Review> reviews = new ArrayList<Review>();
	
	@Column(nullable=false)
	private String boardTitle;
	
	@Column(nullable=false)
	private String boardSubtitle1;
	
	@Column(nullable=false)
	private String boardSubtitle2;
	
	private String boardContent;
	
	private Long boardView;
	
	private LocalDate boardRegdate;
	
	
}
