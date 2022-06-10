package com.cloudrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudrip.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
}
