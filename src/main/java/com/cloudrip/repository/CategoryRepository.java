package com.cloudrip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudrip.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   
   Category findByCategoryId(Long categoryId);
   Category findByCategoryNameContaining(String categoryName);
   Category findByCategoryName(String categoryName);
   
   Page<Category> findByCategoryNameContaining(Pageable pageable, String keyword);
}