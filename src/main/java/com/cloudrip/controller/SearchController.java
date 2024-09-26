package com.cloudrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.CategoryService;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("")
	public String index(Model model) {
		List<Category> list = categoryService.categoryAll();
		model.addAttribute("categorys", list);
		model.addAttribute("test","추천 검색어");
		return "search";
	}
		
	@RequestMapping(value="/{categoryId}",method=RequestMethod.GET)
	public String next(@PathVariable Long categoryId, Model model) {			
		Category category = categoryService.findByCategoryId(categoryId);
		List<Board> board = category.getBoards();
		
		System.out.println("next입니다.");
		System.out.println("===========================");
		System.out.println(board);
		System.out.println("===========================");
		model.addAttribute("boards", board);
		return "search";
	}
	
//	@RequestMapping(value="key",method=RequestMethod.GET)
//	public String form(@RequestParam("key") String categoryId ,Model model) {			
//		List<Board> list = boardService.findAll();
//		System.out.println(list.size());
//		System.out.println("form입니다.");
//		model.addAttribute("boards", list);
//		return "nextsearch";
//	}
		
}

