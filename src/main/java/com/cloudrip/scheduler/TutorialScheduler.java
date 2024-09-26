package com.cloudrip.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import com.cloudrip.controller.MypageController;
import com.cloudrip.domain.Review;
import com.cloudrip.service.ReviewService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
public class TutorialScheduler implements ApplicationContextAware,BeanNameAware{
	@Autowired
	private ReviewService reviewService;
	
	private ApplicationContext applicationContext;
	private String beanName;
	
	@Autowired
	private MypageController mypageController;
	
	@Scheduled(fixedDelay = 1000l)
	public void executeJob() {
			System.out.println("나 실행되면 안돼");
			if(mypageController.getOnOff().equals("scheduleOn")) {
				MypageController mypageController = new MypageController();
				List<Review> reviewList =reviewService.findByReviewOneMinute();
				if(reviewList.isEmpty()) {
					return;
				}
				ArrayList<JSONObject> jsonArrayList = new ArrayList<>();
				try {
					for (Review review : reviewList) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("document",review.getReviewContent());
						jsonObject.put("id",review.getReviewId());
						jsonArrayList.add(jsonObject);
					}
				}catch(JSONException e) {
					e.printStackTrace();
				}
				JSONObject bellInJsonObject = new JSONObject();
				bellInJsonObject.put("bell_in", jsonArrayList);
				String result = null;
				try {
					System.out.println(bellInJsonObject);
					result=mypageController.sendJSON(bellInJsonObject);
					System.out.println(result);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				org.json.JSONObject outputJObject = new org.json.JSONObject(result);
				JSONArray outputJArray = outputJObject.getJSONArray("bell_out");
				for(int i=0; i<outputJArray.length(); i++) {
					org.json.JSONObject obj = outputJArray.getJSONObject(i);
					int label= obj.getInt("label");
					int id = obj.getInt("id");
					reviewService.updateFilterOn(new Long(id),new Long(label));
					}
			}
	}
	public void startScheduledTask() {
	    ScheduledAnnotationBeanPostProcessor bean = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
        bean.postProcessAfterInitialization(this, beanName);	
	}
	public void stopScheduledTask()
    {
        ScheduledAnnotationBeanPostProcessor bean = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
        bean.postProcessBeforeDestruction(this, beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String beanName)
    {
        this.beanName = beanName;
    }
    


	

}
