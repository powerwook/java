package com.cloudrip.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.CategoryService;

@Controller
@RequestMapping("/nextsearch")
public class NextSearchController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String form(@RequestParam("key") String categoryId, Model model) {			
		if(categoryId=="") {
			String msg = "검색어를 입력해주세요.";
			model.addAttribute("msg",msg);
			return "categoryAlert";
		} 
		else if(categoryId.chars().allMatch(Character :: isDigit)) { //숫자면 
			Category category = categoryService.findByCategoryId(Long.parseLong(categoryId));
			List<Board> board = category.getBoards();
			model.addAttribute("boards", board);
			return "nextsearch";
		}
		else {
			Category category = categoryService.findByCategoryName(categoryId);
			if(category == null) {
				String msg ="해당하는 카테고리가 없습니다.";
				model.addAttribute("msg",msg);
				return "categoryAlert";
			}
			List<Board> board = category.getBoards();
			model.addAttribute("boards", board);
			return "nextsearch";
		}
	}
//	@RequestMapping(value="",method=RequestMethod.GET)
//	public String search(@RequestParam("key") String key, Model model) {			
//		List<Board> searchList = boardService.search(key, key, key);
//		model.addAttribute("boards", searchList);
//		return "nextsearch";
//	}
}
