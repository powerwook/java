package com.cloudrip.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import lombok.Data;

@Data
@Entity
@Table(name="category")
@Transactional
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	private String categoryName;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, mappedBy="category")
	private List<Board> boards = new ArrayList<Board>();
}
