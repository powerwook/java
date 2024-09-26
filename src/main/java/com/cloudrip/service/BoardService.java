package com.cloudrip.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.domain.Review;
import com.cloudrip.repository.BoardRepository;
import com.cloudrip.repository.ReviewRepository;
@Service
public class BoardService {
   
   @Autowired
   private BoardRepository boardRepository;
   
   @Autowired
   private ReviewRepository reviewRepository;
   
   public Board findByBoardTitle(String boardTitle) {
	   Board board = boardRepository.findByBoardTitle(boardTitle);
	   return board;
   }
   
   
   public Board findByBoardId(Long boardId) {
      Board board = new Board();
      board = boardRepository.findByBoardId(boardId);
      return board;
   }

   
   public List<Board> findAll(){
      return boardRepository.findAll();
   }
   
   @Transactional
   public Board createBoard(Long boardId,String boardTitle,
         String boardSubtitle1,String boardSubtitle2, String boardContent) {
      Board board =new Board();
      board.setBoardId(boardId);
      board.setBoardTitle(boardTitle);
      board.setBoardSubtitle1(boardSubtitle1);
      board.setBoardSubtitle2(boardSubtitle2);
      board.setBoardContent(boardContent);
      board.setBoardView(0l);
      boardRepository.save(board);
      return board;
   }
   
   public void save(Board board) {
      boardRepository.save(board);
   }
   
   // 관리자 보드 추가
   public void insertBoard(Board board, Category category) {
      
      LocalDateTime regDate = LocalDateTime.now();
      
      board.setCategory(category);
      board.setBoardView(0l);
      board.setBoardRegdate(regDate);
      boardRepository.save(board);
   }
   
   // search 보드 검색
   public Page<Board> findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(Pageable pageable, String searchText,String searchText2) {
      return boardRepository.findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(pageable, searchText, searchText2);
   }
   
   // 홈 Top5 보드 리스트
   public List<Board> findTop5BoardList() {
         
         List<Board> boards = boardRepository.findTop5ByOrderByBoardView();
         System.out.println("============================");
         System.out.println(boards);
         System.out.println("============================");
         return boards;
      }
   
   // 관리자 보드 리스트 (페이징)
   public List<Board> searchBoard(String keyword1, String keyword2, String keyword3, String keyword4) {
      return boardRepository.findByBoardTitleContainingOrBoardSubtitle1ContainingOrBoardSubtitle2ContainingOrBoardContentContaining(keyword1, keyword2, keyword3, keyword4);
   }
   
   // 보드 삭제 (리뷰도 같이)
   public void deleteBoard(Long boardId) {
      Board board = boardRepository.findByBoardId(boardId);
      List<Review> BoardReviewList = board.getReviews();
//      사용자가 쓴 리뷰가 있다면 그 리뷰들을 먼저 삭제해줘야 함. 
      if(BoardReviewList!=null) {
         for (Review review : BoardReviewList) {
            reviewRepository.delete(review);
         }
      }
      boardRepository.delete(board);
   }
   
   
   public void updateBoard(Board board, Category category) {
      System.out.println("***************************************");
      System.out.println(board);
      System.out.println(category);
      
      Long boardId = board.getBoardId();
      String boardTitle = board.getBoardTitle();
      String boardSubtitle1 = board.getBoardSubtitle1();
      String boardSubtitle2 = board.getBoardSubtitle2();
      String boardContent = board.getBoardContent();
      Long boardView = board.getBoardView();
      // LocalDateTime boardRegdate = LocalDateTime.now();
      // BindingResultError ( 형변환 실패 )
      LocalDateTime boardRegdate = board.getBoardRegdate();
      
      Long categoryId = category.getCategoryId();
      
      
      boardRepository.updateBoard(boardId, boardTitle, boardSubtitle1, boardSubtitle2, boardContent, boardView, boardRegdate, categoryId);
      
//      Board boardEntity = boardRepository.findByBoardId(boardId);
//      System.out.println(boardEntity);
//      boardEntity.setBoardTitle(boardTitle);
//      boardEntity.setBoardSubtitle1(boardSubtitle1);
//      boardEntity.setBoardSubtitle2(boardSubtitle2);
//      boardEntity.setBoardContent(boardContent);
//      boardEntity.setBoardRegdate(boardRegdate);
//      boardEntity.setCategory(category);
   }
         
}