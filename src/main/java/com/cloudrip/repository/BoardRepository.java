package com.cloudrip.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;

public interface BoardRepository extends JpaRepository<Board, Long>{

    public Board findByBoardId(Long boardId);
    
    public Board findByBoardTitle(String boardTitle);
    
    Page<Board>findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(Pageable pageable, String boardSubtitle1, String boardSubtitle2);
    // findByBoardSubtitle1Containing 에 들어간 BoardSubtitle1 를 검색해서 찾아줌 s
    
    @Query("SELECT p FROM Board p ORDER BY p.id DESC")
    List<Board> findAllDesc();
    
    @Query(nativeQuery=true, 
            value="SELECT * FROM board ORDER BY board_view DESC LIMIT 5")
      public List<Board> findTop5ByOrderByBoardView();
    
    List<Board> findByBoardTitleContainingOrBoardSubtitle1ContainingOrBoardSubtitle2ContainingOrBoardContentContaining(String keyword1, String keyword2, String keyword3, String keyword4);
    
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE board b SET "
          +"b.category_id = :categoryId, "
          +"b.board_title = :boardTitle, "
          +"b.board_subtitle1 = :boardSubtitle1, "
          +"b.board_subtitle2 = :boardSubtitle2, "
          +"b.board_content = :boardContent, "
          +"b.board_regdate = :boardRegdate, "
          +"b.board_view = :boardView "
          +"where b.board_id = :boardId", nativeQuery = true)
    void updateBoard(@Param(value = "boardId") Long boardId, @Param(value = "boardTitle") String boardTitle, 
          @Param(value = "boardSubtitle1") String boardSubtitle1, @Param(value = "boardSubtitle2") String boardSubtitle2, 
          @Param(value = "boardContent") String boardContent, @Param(value = "boardView") Long boardView,
          @Param(value = "boardRegdate") LocalDateTime boardRegdate, 
          @Param(value = "categoryId") Long categoryId);
}