package com.cloudrip.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class InitStopScheduler implements CommandLineRunner{

	@Autowired
	private TutorialScheduler tutorialScheduler;
	

//	초기 실행시 스케줄러 동작을 막아줌
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		tutorialScheduler.stopScheduledTask();
		
	}
}
