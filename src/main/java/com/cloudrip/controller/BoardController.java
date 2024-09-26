package com.cloudrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.ReviewService;



@Controller
@RequestMapping("board")
public class BoardController {

	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReviewService reviewService;
	
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
	
//	@GetMapping ("/form")
//	public String form(Model model) {
//		model.addAttribute("board",new Board());		
//		return "board/form";
//	}
	@CrossOrigin(origins="*")
	@GetMapping("/{boardId}")
	public String board(@PathVariable("boardId") Long boardId, Model model
			,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Board board = boardService.findByBoardId(boardId);
		board.setBoardView(board.getBoardView()+1);
		boardService.save(board);
		List<Review> reviewList = board.getReviews();
		List<Review> bestReviewList = reviewService.findBestReview(board);
		try {
			User user= principalDetails.getUser();
			model.addAttribute("user", user);
		}catch(Exception e) {
			String unLoginUser = "로그인 한 사용자만 게시물을 볼 수 있습니다.";
			model.addAttribute("msg",  unLoginUser);
			return "categoryAlert";
		}
			model.addAttribute("board", board);
			model.addAttribute("reviewList", reviewList);
			model.addAttribute("bestReviewList",bestReviewList);
			return "board";
		}
	@PostMapping("/{boardId}/reviewHitAdd/{reviewId}")
	public String reviewHitAdd(@PathVariable("boardId") Long boardId, @PathVariable("reviewId") Long reviewId) {
		System.out.println("*****************************");
		reviewService.reviewHitAdd(reviewId);
		return "redirect:/";
	}
	
	@PostMapping("/{boardId}/reviewHitMinus/{reviewId}")
	public String reviewHitMinus(@PathVariable("boardId") Long boardId, @PathVariable("reviewId") Long reviewId) {
		System.out.println("*****************************");
		reviewService.reviewHitMinus(reviewId);
		return "redirect:/";
	}
}
