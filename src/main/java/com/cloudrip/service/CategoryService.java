package com.cloudrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Board;
import com.cloudrip.domain.Category;
import com.cloudrip.domain.Review;
import com.cloudrip.repository.BoardRepository;
import com.cloudrip.repository.CategoryRepository;
import com.cloudrip.repository.ReviewRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public List<Category> categoryAll() {
		return categoryRepository.findAll();
		
	}
	public Category findByCategoryId(Long categoryId) {
		return categoryRepository.findByCategoryId(categoryId);
	}
	
	public Category findByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}
	
	public Category findByCategoryNameContaining(String categoryName) {
		return categoryRepository.findByCategoryNameContaining(categoryName);
	}
	
	public Page<Category> findByCategoryNameContaining(Pageable pageable, String keyword) {
	      return categoryRepository.findByCategoryNameContaining(pageable, keyword);
	   }
	
	public Category create(String categoryName) {
		Category category = new Category();
		category.setCategoryName(categoryName);
		categoryRepository.save(category);
		return category;
	}
	
	
	public void deleteCategory(Long categoryId) {
	      Category category= categoryRepository.findByCategoryId(categoryId);
	      List<Board> CategoryBoardList =category.getBoards();
	      if(CategoryBoardList !=null) {
//	    	  보드 삭제하기전 리뷰먼저 삭제
	         for (Board board : CategoryBoardList ) {
	        	 List<Review> BoardReviewList =board.getReviews();
	        	 for(Review review : BoardReviewList) {
	        		 reviewRepository.delete(review);
	        	 }
	            boardRepository.delete(board);
	         }
	      }
	      categoryRepository.delete(category);
	   }
	
	   public void save(Category category) {
		      categoryRepository.save(category);
		   }
		   
	   public void updateCategory(Category category) {
	      Category categoryEntity = findByCategoryId(category.getCategoryId());
	      categoryEntity.setCategoryName(category.getCategoryName());
	      categoryRepository.save(categoryEntity);
	   }
		   

}
