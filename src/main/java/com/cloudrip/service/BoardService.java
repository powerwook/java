package com.cloudrip.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Board;
import com.cloudrip.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
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
}
