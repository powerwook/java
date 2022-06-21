package com.cloudrip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudrip.config.auth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.services.BoardService;
import com.cloudrip.services.ReviewService;
import com.cloudrip.services.WebSocketChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReviewService reviewService;
	
//	@Autowired
//	private WebSocketChatService webSocketChatService;
	

	
	@GetMapping("/{boardId}")
	public String board(@PathVariable("boardId") Long boardId, Model model
			,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Board board = boardService.findByBoardId(boardId);
		List<Review> reviewList = board.getReviews();
		User user= principalDetails.getUser();
		model.addAttribute("board", board);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("user", user);
		return "board";
	}
	
//	@PostMapping("/review")
//	@ResponseBody
//	public String reviewInsert(@RequestBody Review review) {
//		
//		reviewService.reviewInsert(review);
//		return "board";
//	}
	
//	public Map<String, Object> reviewInsert(@Valid @RequestParam Map<String, Object> review) {
//		reviewService.reviewInsert(review);
//	
//	@PostMapping("/review/")
//	@ResponseBody
//	public Review boardPost(@RequestParam Map<String, Object> review) throws Exception {
//		System.out.println("review@@@@@@@@@@@@@@" + review);
//		Long boardId = Long.parseLong((String) review.get("board"));
//		System.out.println(boardId);
//		Board boardEntity = boardService.findByBoardId(boardId);
//		
//		Review reviewEntity = reviewService.reviewInsert(review, boardEntity);
//		
//		
//		System.out.println(review);
//		
//		Map<String, ?> board=(Map<String,?>) review.get("board");
//		JSONObject jsonObject = new JSONObject(board);
//		String boardIdString = jsonObject.getAsString("boardId");
//		Long boardId=Long.parseLong(boardIdString);
//		Board boardEntity=boardService.findByBoardId(boardId);
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			 reviewEntity = objectMapper.readValue(review, reviewEntity.getClass());
//			 System.out.println(reviewEntity);
//			 reviewService.reviewInsert(reviewEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		Map<String, Object> reviewList = new HashMap<>();
		
//		Review review1 = new Review();
//		Review reviewEntity = reviewService.reviewInsert(review, boardEntity);
//		System.out.println(review1.toString());
//		return reviewEntity;
//		return reviewEntity;
//	}
	
	
}
