package com.cloudrip.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cloudrip.domain.Board;
import com.cloudrip.service.BoardService;

@Controller
public class ListController {

	@Autowired
	private BoardService boardService;
	

	
	@GetMapping("/list")
	public String list(Model model, Authentication auth){	//auth 라는 변수로 꺼내줌  
		System.out.println(auth.getAuthorities()); // 가지고 있는 모든 롤을 출
		boolean hasAdmin = false; // 셋팅값 기본
		for (GrantedAuthority authority : auth.getAuthorities()) { 
			hasAdmin = authority.getAuthority().equals("ROLE_ADMIN");
			if(hasAdmin) {
				break;
			}
		}	
		List<Board> boards = boardService.findAll();
		model.addAttribute("boards",boards);
		model.addAttribute("hasAdmin",hasAdmin);
		return "list"; 
	}
	
	@GetMapping("/list/form")
	public String form(Model model) {
		model.addAttribute("board", new Board());
		return "form";
	}
	@PostMapping("/list/form")
	public String greetingSubmit(@ModelAttribute Board board) {
		boardService.save(board);
		return "redirect:/list";
	}
}
