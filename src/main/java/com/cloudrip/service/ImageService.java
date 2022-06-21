package com.cloudrip.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Image;
import com.cloudrip.domain.User;
import com.cloudrip.dto.ImageUploadDto;
import com.cloudrip.repository.ImageRepository;
import com.cloudrip.repository.UserRepository;

@Service
public class ImageService {

	@Value("${file.path}")
	private String uploadFolder;
	
	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void ImageUpload(ImageUploadDto imageUploadDto,
			PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지파일이름 : "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		
		try {
			System.out.println(imageFilePath);
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
			System.out.println("hi");
		}catch(Exception e) {
			e.printStackTrace();
		}
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		System.out.println("image생성됨?");
		User user = principalDetails.getUser();
		user.setImage(image);
		imageRepository.save(image);
		userRepository.save(user);
		
	}
}
