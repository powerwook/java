package com.cloudrip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

//web mvc에서 낚아채서 주소바꿈.
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ //web 설정 파일
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			//file:///d:/upload/
			registry
				.addResourceHandler("/upload/**") //페이지에서 /upload/** 이런 주소패턴이 나오면 발동
				.addResourceLocations("file:///"+uploadFolder) //실제 물리적 경로에 내부적 매핑
				.setCachePeriod(60*10*6) //제공된 리소스는 브라우저에 1시간동안 캐싱
				.resourceChain(true) //체인은 resorceChain 메서드로 구성
				.addResolver(new PathResourceResolver()); //얘를 유일한 ResourceResolver로 등록
			
				
		}

}
