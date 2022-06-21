package com.cloudrip.service;

import java.util.List;

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
	
	public void save(Board board) {
		boardRepository.save(board);
		
	}
	
	public Board findByBoardId(Long boardId) {
		return boardRepository.findByBoardId(boardId);
	}

}
