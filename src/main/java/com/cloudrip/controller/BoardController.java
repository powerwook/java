package com.cloudrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cloudrip.domain.Board;

import com.cloudrip.service.BoardService;



@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	

	
	@GetMapping ("/board")
	public String board(Model model) {
		List<Board> boards = boardService.findAll();
		model.addAttribute("boards",boards);		
		return "board";
	}
	
	@GetMapping ("/board/form")
	public String form(Model model) {
		model.addAttribute("board",new Board());		
		return "board/form";
	}
	
	@PostMapping("/board/form")
	public String greetingSubmit(@ModelAttribute("board") Board board, Model model) {
		boardService.save(board);
		model.addAttribute("board", board);
		return "redirect:/board";
	}
	
	@GetMapping("/board/{boardId}")
	public String boarddetail(@PathVariable Long boardId, Model model) {		
		Board board = boardService.findByBoardId(boardId);
		model.addAttribute("board", board);
		return "board";
		
	}
	
}
