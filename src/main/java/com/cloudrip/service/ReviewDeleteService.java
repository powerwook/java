package com.cloudrip.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TimerTask;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.Review;
import com.cloudrip.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@ServerEndpoint(value="/mypage/review")
public class ReviewDeleteService {

	private static ReviewService reviewService;
	
	@Autowired
	public void setReviewService(ReviewService reviewService) {
		ReviewDeleteService.reviewService = reviewService;
	}

	
	private static Set<Session> clients = Collections
			.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session s) {
		System.out.println("open session :"+s.toString());
		
		if(!clients.contains(s)) {
			clients.add(s);
			System.out.println("session open : "+s);
		}else {
			System.out.println("이미 연결된 session 임!!");
		}
	}
	
	@OnMessage
	public void onMessage(String msg,Session session) throws Exception{
		ReviewDeleteService.reviewService.deleteReview(Long.parseLong(msg));
		for(Session s : clients) {
			System.out.println("send data : "+msg);
			s.getBasicRemote().sendText(msg);
		}
		ReviewDeleteService.onClose(session);
		
		
	}
	
	@OnClose
	public static void onClose(Session s) {
		System.out.println("session close : "+s);
		clients.remove(s);
	}
	
	@OnError
	public void onError(Throwable t) throws Exception {
		System.out.println(t.toString());
	}
}
