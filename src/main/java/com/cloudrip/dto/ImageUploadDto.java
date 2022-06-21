package com.cloudrip.dto;

import org.springframework.web.multipart.MultipartFile;

import com.cloudrip.domain.Image;
import com.cloudrip.domain.User;

import lombok.Data;

@Data
public class ImageUploadDto {

	
	private MultipartFile file;
	
	public Image toEntity(String postImageUrl,User user) {
		return Image.builder()
				.postImageUrl(postImageUrl)
				.userImage(user)
				.build();
	}
}
