package com.cloudrip.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cloudrip.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	public Board findByBoardId(Long boardId);
	
	 Page<Board>findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(Pageable pageable, String boardSubtitle1, String boardSubtitle2);
	 // findByBoardSubtitle1Containing 에 들어간 BoardSubtitle1 를 검색해서 찾아줌 s
	 
    @Query("SELECT p FROM Board p ORDER BY p.id DESC")
    List<Board> findAllDesc();
    
    @Query(nativeQuery=true, 
            value="SELECT * FROM board ORDER BY board_view DESC LIMIT 5")
      public List<Board> findTop5ByOrderByBoardView();
}
