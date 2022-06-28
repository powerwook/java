package com.cloudrip.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Image;
import com.cloudrip.domain.User;
import com.cloudrip.dto.ImageUploadDto;
import com.cloudrip.repository.ImageRepository;
import com.cloudrip.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ImageService {
	
	
	@Autowired
	private AmazonS3Client amazonS3Client;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void ImageUpload(ImageUploadDto imageUploadDto,
			PrincipalDetails principalDetails,MultipartFile multipartFile) {
		UUID uuid = UUID.randomUUID();
		String directory = "upload/";
		System.out.println(imageUploadDto.getFile().getOriginalFilename());
		String imageFileName = directory+"/"+uuid+imageUploadDto.getFile().getOriginalFilename();
//		로컬에저장
		File convertFile = new File("D:\\upload"+"/"+uuid+"_"+imageUploadDto.getFile().getOriginalFilename());
		try {
			convertFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convertFile);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		S3로파일 업로드
		String S3fileName =directory+uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		amazonS3Client.putObject(new PutObjectRequest(bucket, S3fileName , convertFile));
		User user = principalDetails.getUser();
		Image image =  new Image();
		System.out.println();
		image.setPostImageUrl(amazonS3Client.getUrl(bucket,S3fileName).toString());
		user.setImage(image);
		imageRepository.save(image);
		userRepository.save(user);
		
	}
	
}
