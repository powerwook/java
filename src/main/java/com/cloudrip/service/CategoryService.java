package com.cloudrip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Category;
import com.cloudrip.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> categoryAll() {
		return categoryRepository.findAll();
		
	}
	public Category findByCategoryId(Long categoryId) {
		return categoryRepository.findByCategoryId(categoryId);
	}
	
	public Category findByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}
}
