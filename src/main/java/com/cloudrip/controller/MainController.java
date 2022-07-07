package com.cloudrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.domain.User;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.CategoryService;

@Controller
public class MainController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<Board> boards = boardService.findTop5BoardList();
	    model.addAttribute("boardList", boards);

		return "home";
	}
}
