package com.cloudrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.service.BoardService;



@Controller
@RequestMapping("board")
public class BoardController {

	
	@Autowired
	private BoardService boardService;
	
//	@GetMapping ("/board")
//	public String board(Model model) {
//		List<Board> boards = boardService.findAll();
//		model.addAttribute("boards",boards);		
//		return "board";
//	}
//	
//	@PostMapping("/board")
//	public String board(Model model,Long boardId) {
//		Board board = boardService.findByBoardId(boardId);
//		model.addAttribute("board",board);
//		return "board";
//	}
	
	@GetMapping ("/form")
	public String form(Model model) {
		model.addAttribute("board",new Board());		
		return "board/form";
	}
	
	@PostMapping("/form")
	public String insertBoard(@ModelAttribute("board") Board board, Model model) {
		Board boardEntity = boardService.insertBoard(board);
		model.addAttribute("board", boardEntity);
		return "redirect:/board";
	}
	
	@GetMapping("/{boardId}")
	public String board(@PathVariable("boardId") Long boardId, Model model
			,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Board board = boardService.findByBoardId(boardId);
		board.setBoardView(board.getBoardView()+1);
		List<Review> reviewList = board.getReviews();
		User user= principalDetails.getUser();
		model.addAttribute("board", board);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("user", user);
		return "board";
	}
	
}
