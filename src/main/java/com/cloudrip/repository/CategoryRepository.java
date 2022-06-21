package com.cloudrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudrip.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByCategoryId(Long categoryId);
	Category findByCategoryName(String categoryName);
}
