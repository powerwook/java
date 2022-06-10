package com.cloudrip.domain;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewIds implements Serializable{

	@Column
	private Long boardId;
	@Column
	private Long reviewId;
	
	
}
