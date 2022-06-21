package com.cloudrip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Image;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.SignoutForm;
import com.cloudrip.domain.User;
import com.cloudrip.dto.ImageUploadDto;
import com.cloudrip.repository.UserRepository;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.ImageService;
import com.cloudrip.service.ReviewService;
import com.cloudrip.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("")
	public String mypageGet(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetails userDetails = (PrincipalDetails) principal;
		User user = userDetails.getUser();
		User user2 = userService.findByProviderId(user.getProviderId());
		User newUser = userService.updateUserNickname(user2.getNickname());
		Image image=newUser.getImage();
		model.addAttribute("user",newUser);
		model.addAttribute("image",image);
		return "mypage";
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public String mypagePost(Model model,@RequestBody(required=false) String nickname, User user) {
		System.out.println(nickname);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(nickname, user.getClass());
			User newUser = userService.updateUserNickname(user.getNickname());
			model.addAttribute("user",newUser);
			return "mypage";
		}catch(IOException e) {
			e.printStackTrace();
			return "mypage";
		}
	}
	
	@RequestMapping(value="/image",method=RequestMethod.POST)
	public ModelAndView mypage(
			ModelAndView modelAndView,ImageUploadDto imageUploadDto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetails userDetails = (PrincipalDetails) principal;
		imageService.ImageUpload(imageUploadDto, userDetails);
		modelAndView.setViewName("redirect:/mypage");
		return modelAndView;
	}
	
	@GetMapping("/review")
	public String mypageReview(Model model,Review review) {
		log.info("나는 log.info입니다"+"@ChatController,chat GET()");
		List<Review> reviews=reviewService.findAllReview();
		System.out.println(reviews);
		model.addAttribute("reviews",reviews);
		return "review";
	}
	
	@RequestMapping(value="/review" , method=RequestMethod.POST)
	public String mypageReviewPost(@RequestParam Long boardId,
			String boardTitle,String boardSubtitle1,String boardSubtitle2
			) {
		Board board = boardService.createBoard(boardId,boardTitle,boardSubtitle1,boardSubtitle2);
		reviewService.create(board);
		return "review";
	}
	
	
	@PutMapping(value="/review/{reviewId}")
	public String mypageReviewPost(@PathVariable("reviewId") Long reviewId,Model model) {
		System.out.println("reviewId"+reviewId);
			reviewService.deleteReview(reviewId);
			List<Review> reviews=reviewService.findAllReview();
			model.addAttribute("reviews",reviews);
			return "review";
	}
	
	
	
	
}
