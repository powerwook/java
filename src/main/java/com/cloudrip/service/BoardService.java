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
import com.cloudrip.repository.BoardRepository;
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public Board findByBoardId(Long boardId) {
		Board board = new Board();
		board = boardRepository.findByBoardId(boardId);
		return board;
	}

	public void create(Board board) {
		boardRepository.save(board);
	}
	public List<Board> findAll(){
		return boardRepository.findAll();
	}
	@Transactional
	public Board createBoard(Long boardId,String boardTitle,
			String boardSubtitle1,String boardSubtitle2) {
		Board board =new Board();
		board.setBoardId(boardId);
		board.setBoardTitle(boardTitle);
		board.setBoardSubtitle1(boardSubtitle1);
		board.setBoardSubtitle2(boardSubtitle2);
		boardRepository.save(board);
		return board;
	}
	
	public void save(Board board) {
		boardRepository.save(board);
	}
	
	public Board insertBoard(Board board,Category category) {
		
		LocalDate regDate = LocalDate.now();
		
		board.setCategory(category);
		board.setBoardView(0l);
		board.setBoardRegdate(regDate);
		boardRepository.save(board);
		
		return board;
	}
	
	public Page<Board> findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(Pageable pageable, String searchText,String searchText2) {
		return boardRepository.findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(pageable, searchText, searchText2);
	}
	
	public List<Board> findTop5BoardList() {
	      
	      List<Board> boards = boardRepository.findTop5ByOrderByBoardView();
	      System.out.println("============================");
	      System.out.println(boards);
	      System.out.println("============================");
	      return boards;
	   }

}