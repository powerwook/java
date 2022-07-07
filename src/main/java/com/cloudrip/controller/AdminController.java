package com.cloudrip.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.domain.Review;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.CategoryService;
import com.cloudrip.service.ReviewService;

@Controller
@RequestMapping(value="admin")
public class AdminController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/board")
	public String BoardList(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails ,
			@PageableDefault(size = 4)Pageable pageable,@RequestParam(required = false, defaultValue = "") String searchText, String searchText2){
		if(!principalDetails.getUser().getRoles().equals("ROLE_ADMIN")) {
		model.addAttribute("msg","당신은 관리자가 아닙니다. 관리자 계정으로 로그인 해주세요.");	
		return "categoryAlert";
		}
		Page<Board> boards = boardService.findByBoardSubtitle1ContainingOrBoardSubtitle2Containing(pageable, searchText, searchText2);
		System.out.println("---------------------------");
		for (Board board : boards) {
			System.out.println(board);
		}
		System.out.println("---------------------------");
		int startPage = Math.max(1,boards.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -4
		int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 10);
		model.addAttribute("boards",boards);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "adminBoard"; 
		

	}
	
	@GetMapping("/board/add")
	public String BoardAddGet(Model model) {
		model.addAttribute("board", new Board());
		return "boardAdd";
	}
	@PostMapping("/board/add")
	public String BoardAddPost(@ModelAttribute Board board,String categoryName) {
		Category category=categoryService.findByCategoryName(categoryName);
		if (category==null) {
			Category newCategory=categoryService.create(categoryName);
			Board boardEntity = boardService.insertBoard(board,newCategory);
		}else {
			Board boardEntity = boardService.insertBoard(board,category);
		}
		return "redirect:/admin/board";
	}
	
	@GetMapping("/review")
	public String mypageReview(Model model,
			@PageableDefault(size = 10)Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String reviewContent) {
		Page<Review> reviews=reviewService.findByReviewContentContaining(pageable,reviewContent);
		System.out.println("----------------------");
		for (Review review : reviews) {
			System.out.println(review);
		}
		System.out.println("----------------------");
		
		int startPage = Math.max(1,reviews.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -10
		int endPage = Math.min(reviews.getTotalPages(),reviews.getPageable().getPageNumber() + 10);
		
		model.addAttribute("reviews",reviews);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "adminReview";
	}
	
//	@PostMapping("/form")
//	public String insertBoard(@ModelAttribute("board") Board board, Model model) {
//		Board boardEntity = boardService.insertBoard(board);
//		model.addAttribute("board", boardEntity);
//		return "redirect:/board";
//	}
	
}
