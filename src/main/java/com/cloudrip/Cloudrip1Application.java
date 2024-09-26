package com.cloudrip;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.cloudrip.scheduler.TutorialScheduler;


@EnableScheduling //sheduling 활성화
@SpringBootApplication
public class Cloudrip1Application {
	// 깃 연습용
	// 강진호 롤 허접

	
	public static void main(String[] args) {
		SpringApplication.run(Cloudrip1Application.class, args);
	}

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	



}
