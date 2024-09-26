//package com.cloudrip.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import com.cloudrip.service.WebSocketChatService;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer{
//	
//	@Autowired
//	WebSocketChatService webSocketChatService;
//
//	@Override
//	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		System.out.println("hello==============================================");
//		registry.addHandler(webSocketChatService, "/board/{boardId}")
//		.setAllowedOrigins("*").withSockJS();
//	}
//	
//	
//	
//}
