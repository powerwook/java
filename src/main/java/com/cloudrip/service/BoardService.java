package com.cloudrip.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
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
//	/*search*/
//	public List<Board> search(String boardTitle, String boardSubtitle1, String boardSubtitle2) {
//		List<Board> searchList = boardRepository.findByTitleContaining(boardTitle, boardSubtitle1, boardSubtitle2);
//		return searchList;
//	}


}
