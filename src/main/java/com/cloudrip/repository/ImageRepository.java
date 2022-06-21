package com.cloudrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudrip.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{

}
