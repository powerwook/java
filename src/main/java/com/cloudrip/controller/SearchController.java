package com.cloudrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudrip.domain.Board;
import com.cloudrip.service.BoardService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private BoardService boardService;
	
	
	
	@GetMapping("")
	public String search() {
		return "search";
	}

	@GetMapping("/next")
	public String searchNext(Model model){
		List<Board> boardList = boardService.findAll();
		System.out.println(boardList);
		model.addAttribute("boards",boardList);
		
		return "nextsearch"; 
	}
	
	@PostMapping("/next")
	public String searchNextPost(Board board){
		System.out.println(board);
		boardService.create(board);
		return "nextsearch";
	}
}
