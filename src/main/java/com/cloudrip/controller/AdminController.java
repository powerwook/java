package com.cloudrip.controller;


import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.User;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.CategoryService;
import com.cloudrip.service.ReviewService;
import com.cloudrip.service.UserService;

@Controller
@RequestMapping(value="admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CategoryService categoryService;
	
	//admin
	@GetMapping(value={"","/"})
	public String adminMain(Model model,@AuthenticationPrincipal PrincipalDetails principalDetails ) {
		if(!principalDetails.getUser().getRoles().equals("ROLE_ADMIN")) {
			model.addAttribute("msg","당신은 관리자가 아닙니다. 관리자 계정으로 로그인 해주세요.");	
			return "categoryAlert";
		}
		return "adminMain";
	}
	
	
	
	   // user 기능
	   @GetMapping("/user")
	   public String adminUser(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails,
	         @PageableDefault(size = 10)Pageable pageable, 
	         @RequestParam(required = false, defaultValue = "") String search) {
	      if(principalDetails == null){
	         model.addAttribute("msg", "세션 만료.");
	         return "categoryAlert";
	      } else if(!principalDetails.getUser().getRoles().equals("ROLE_ADMIN")) {
	         model.addAttribute("msg","당신은 관리자가 아닙니다. 관리자 계정으로 로그인 해주세요.");   
	         return "categoryAlert";
	      }
	      
//	      Page<User> users = null;
//	      if (userSearch == "email") {
//	         users = userService.findByEmailContaining(pageable, search);
//	      } else if(userSearch == "nickname") {
//	         users = userService.findByNicknameContaining(pageable, search);
//	      } else if(userSearch == "roles") {
//	         users = userService.findByRolesContaining(pageable, search);
//	      }else {
//	         users = userService.findAll(pageable);
//	      }
	      
	      Page<User> users = userService.findByEmailContainingOrNicknameContainingOrRolesContaining(pageable, search, search, search);
	      
	      System.out.println("---------------------------");
	      for (User user : users) {
	         System.out.println(user);
	      }
	      System.out.println("---------------------------");
	      // System.out.println(userSearch);
	      System.out.println(search);
	      
	      
	      int startPage = Math.max(1, users.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -4
	      int endPage = Math.min(users.getTotalPages(),users.getPageable().getPageNumber() + 10);
	      model.addAttribute("users", users);
	      model.addAttribute("startPage", startPage);
	      model.addAttribute("endPage", endPage);
	      
	      return "adminUser";
	   }
	   
	   
	   @PostMapping("/user/delete")
	   public String adminUserDelete(@RequestParam("valueArr") String[] valueArr) {
	      
	      System.out.println("==========/admin/user/delete==========");
	      System.out.println(valueArr);
	      System.out.println("======================================");
	      int size = valueArr.length;
	      for(int i = 0; i < size; i++) {
	         userService.deleteUser(valueArr[i]);
	      }
	      
	      return "redirect:";
	   }
	
	
	
	
	

	   // category 기능
	   @GetMapping("/category")
	   public String adminCategory(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails,
	         @PageableDefault(size = 10)Pageable pageable, 
	         @RequestParam(required=false, defaultValue="") String search) {
	      if(principalDetails == null){
	         model.addAttribute("msg", "세션 만료.");
	         return "categoryAlert";
	      } else if(!principalDetails.getUser().getRoles().equals("ROLE_ADMIN")) {
	         model.addAttribute("msg","당신은 관리자가 아닙니다. 관리자 계정으로 로그인 해주세요.");   
	         return "categoryAlert";
	      }
	      Page<Category> categorys = categoryService.findByCategoryNameContaining(pageable, search);
	      
	      int startPage = Math.max(1, categorys.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -4
	      int endPage = Math.min(categorys.getTotalPages(),categorys.getPageable().getPageNumber() + 10);
	      model.addAttribute("categorys", categorys);
	      model.addAttribute("startPage", startPage);
	      model.addAttribute("endPage", endPage);
	      
	      return "adminCategory";
	   }
	   
	   @GetMapping("/category/insert")
	   public String adminCategoryInsert(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
	      if(!principalDetails.getUser().getRoles().equals("ROLE_ADMIN")) {
	         model.addAttribute("msg", "세션 만료.");   
	         return "categoryAlert";
	      } else {
	         
	         return "adminCategoryInsert";
	      }
	      
	   }
	   
	   @PostMapping("/category/insert")
	   public String adminCategoryInsert(@ModelAttribute Category category) {
	      
	      categoryService.save(category);
	      
	      return "redirect:/admin/category";
	   }
	   
	   
	   @GetMapping("/category/update/{categoryId}")
	   public String adminCategoryUpdate(@PathVariable Long categoryId, Model model) {
	      
	      Category category = categoryService.findByCategoryId(categoryId);
	      model.addAttribute("category", category);
	      return "adminCategoryUpdate";
	   }
	   
	   @PostMapping("/category/update")
	   public String adminCategoryUpdate(@Valid @ModelAttribute Category category) {
	      System.out.println("*******************************");
	      System.out.println(category.getCategoryId());
	      System.out.println(category);
	      
	      categoryService.save(category);
	      
	      
	      /*
	      Category category = categoryService.findByCategoryName(categorySelect);
	      boardService.updateCategory(category);
	      */
	      return "redirect:/admin/category";
	   }
	   
	   @PostMapping("/category/delete")
	   public String adminCategoryDelete(@RequestParam("valueArr") Long[] valueArr) {         
	      System.out.println("==========/admin/category/delete==========");
	      System.out.println(valueArr);
	      System.out.println("======================================");
	      int size = valueArr.length;
	      for(int i = 0; i < size; i++) {
	         categoryService.deleteCategory(valueArr[i]);
	      }
	      
	      return "redirect:";
	   }
	   
	
	
	   // board 기능
	   @GetMapping("/board")
	   public String BoardList(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails ,
	         @PageableDefault(size = 10)Pageable pageable,@RequestParam(required = false, defaultValue = "") String searchText, String searchText2){
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
	   

	   @PostMapping("/board/delete")
	   public String adminBoardDelete(@RequestParam("valueArr") Long[] valueArr) {         
	      System.out.println("==========/admin/user/delete==========");
	      System.out.println(valueArr);
	      System.out.println("======================================");
	      int size = valueArr.length;
	      for(int i = 0; i < size; i++) {
	         boardService.deleteBoard(valueArr[i]);
	      }
	      
	      return "redirect:";
	   }
	      
	      
	   
	   @GetMapping("/board/insert")
	   public String BoardAddGet(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
	      if(!principalDetails.getUser().getRoles().equals("ROLE_ADMIN")) {
	         model.addAttribute("msg","세션 만료.");   
	         return "categoryAlert";
	      }
	      List<Category> categoryList = categoryService.categoryAll();
	      model.addAttribute("categoryList", categoryList);
	      return "adminBoardInsert";
	   }
	   
	   @PostMapping("/board/insert")
	   public String BoardAddPost(String categorySelect, @ModelAttribute Board board) {
	      
	      // CategoryName null값으로 받아와짐. 이것부터 수정.
	      System.out.println(board);
	      System.out.println("************************************");
	      System.out.println("categoryName : " + categorySelect);
	      System.out.println("************************************");
	      
	      Category category = categoryService.findByCategoryName(categorySelect);
//	      if (category == null) {
//	         Category newCategory = categoryService.create(categoryName);
//	         boardService.insertBoard(board, newCategory);
//	      }else {
	      
	      boardService.insertBoard(board, category);
	      
//	      }
	      return "redirect:/admin/board";
	   }
	   
	   @GetMapping("/board/update/{boardId}")
	   public String boardUpdate(@PathVariable Long boardId, Model model) {
	      
	      List<Category> categoryList = categoryService.categoryAll();
	      Board board = boardService.findByBoardId(boardId);
	      
	      model.addAttribute("board", board);
	      model.addAttribute("categoryList", categoryList);
	      
	      return "adminBoardUpdate";
	   }
	   
	   
	   @PostMapping("/board/update")
	   public String boardUpdate(@Valid @ModelAttribute Board board, String categorySelect) {
	      System.out.println("*******************************");
	      System.out.println(board.getBoardRegdate());
	      System.out.println(board);
	      System.out.println(categorySelect);
	      Category category = categoryService.findByCategoryName(categorySelect);
	      boardService.updateBoard(board, category);
	      
	      return "redirect:/admin/board";
	   }
	
	//review
	@GetMapping("/review")
	public String mypageReview(Model model,
			@PageableDefault(size = 10)Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String reviewSearch) {
			//숫자인경우
			if(reviewSearch.chars().allMatch(Character :: isDigit) && !reviewSearch.equals("")){
				//0을입력한 경우
				if(reviewSearch.equals("0")) {
					Page<Review> reviews = reviewService.findByReviewNoFltContaining(pageable);
					int startPage = Math.max(1,reviews.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -10
					int endPage = Math.min(reviews.getTotalPages(),reviews.getPageable().getPageNumber() + 10);
					
					model.addAttribute("reviews",reviews);
					model.addAttribute("startPage", startPage);
					model.addAttribute("endPage", endPage);
				//1을입력한경우
				}else {
					Page<Review> reviews = reviewService.findByReviewFltContaining(pageable);
					int startPage = Math.max(1,reviews.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -10
					int endPage = Math.min(reviews.getTotalPages(),reviews.getPageable().getPageNumber() + 10);
					
					model.addAttribute("reviews",reviews);
					model.addAttribute("startPage", startPage);
					model.addAttribute("endPage", endPage);
					
				}
			}else {
				Board board = boardService.findByBoardTitle(reviewSearch);
				User user = userService.findByNickname(reviewSearch);
				//보드제목으로입력한경우
				if(board!=null) {
					List<Review> boardTitleReviews = board.getReviews();
					Page<Review> reviews =new PageImpl<Review>(boardTitleReviews,pageable,boardTitleReviews.size());
					int startPage = Math.max(1,reviews.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -10
					int endPage = Math.min(reviews.getTotalPages(),reviews.getPageable().getPageNumber() + 10);
					
					model.addAttribute("reviews",reviews);
					model.addAttribute("startPage", startPage);
					model.addAttribute("endPage", endPage);
				//유저닉네임으로입력한경우
				}else if(user!=null) {
					List<Review> userNicknameReviews = user.getReviews();
					Page<Review> reviews =new PageImpl<Review>(userNicknameReviews,pageable,userNicknameReviews.size());
					int startPage = Math.max(1,reviews.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -10
					int endPage = Math.min(reviews.getTotalPages(),reviews.getPageable().getPageNumber() + 10);
					
					model.addAttribute("reviews",reviews);
					model.addAttribute("startPage", startPage);
					model.addAttribute("endPage", endPage);
				}else {
					Page<Review> reviews = reviewService.findByReviewContentContaining(pageable,reviewSearch);
					int startPage = Math.max(1,reviews.getPageable().getPageNumber() - 10); // (start값)최소값1 최대값 -10
					int endPage = Math.min(reviews.getTotalPages(),reviews.getPageable().getPageNumber() + 10);
					
					model.addAttribute("reviews",reviews);
					model.addAttribute("startPage", startPage);
					model.addAttribute("endPage", endPage);
				}
			}
			return "adminReview";
	}
	
	@PostMapping("/review/delete")
	   public String adminReviewDelete(@RequestParam("valueArr") Long[] valueArr) {         
	      System.out.println("==========/admin/review/delete==========");
	      System.out.println(valueArr);
	      System.out.println("======================================");
	      int size = valueArr.length;
	      for(int i = 0; i < size; i++) {
	         reviewService.deleteReview(valueArr[i]);
	      }
	      
	      return "redirect:";
	   }
	@PostMapping("/review/update")
	   public String adminReviewUpdate(@RequestParam("valueArr") Long[] valueArr) {         
	      System.out.println("==========/admin/review/delete==========");
	      System.out.println(valueArr);
	      System.out.println("======================================");
	      int size = valueArr.length;
	      for(int i = 0; i < size; i++) {
	         reviewService.eliDeleteReview(valueArr[i]);
	      }
	      
	      return "redirect:../";
	   }
}
