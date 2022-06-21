package com.cloudrip.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Board;
import com.cloudrip.repositories.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public Board findByBoardId(Long boardId) {
		Board board = new Board();
		board = boardRepository.findByBoardId(boardId);
		return board;
	}
	
}
