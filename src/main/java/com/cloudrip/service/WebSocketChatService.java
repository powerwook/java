package com.cloudrip.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.User;


@Service
@ServerEndpoint(value="/board/{boardId}")
public class WebSocketChatService {
   
   private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
   
   private static ReviewService reviewService;
   
   private static BoardService boardService;
   
   private static UserService userService;
   @Autowired
   public void setReviewService(ReviewService reviewService) {
      WebSocketChatService.reviewService= reviewService;
   }
   
   @Autowired
   public void setBoardService(BoardService boardService) {
      WebSocketChatService.boardService=boardService;
   }
   @Autowired
   public void setUserService(UserService userService) {
      WebSocketChatService.userService=userService;
   }
   
   @OnMessage
   public void onMessage(String msg, Session session) throws Exception {
      System.out.println(msg);
      try {
         JSONObject jObject = new JSONObject(msg);
         String boardId = jObject.getString("board");
         String reviewContent = jObject.getString("reviewContent");
         String reviewDebate = jObject.getString("reviewDebate");
         String nickname = jObject.getString("nickname");
         Board board = WebSocketChatService.boardService.findByBoardId(Long.parseLong(boardId));
         System.out.println(board);
         WebSocketChatService.reviewService.reviewInsert(reviewContent,reviewDebate,nickname,board);
      }catch(JSONException e) {
         e.printStackTrace();
      }
      System.out.println("receive message : " + msg);
//      for (Session s : clients) {
//         System.out.println("send data : " + msg);
//         s.getBasicRemote().sendText(msg);
//         
//      }      
//      WebSocketChatService.onClose(session);
      
      /*
      clients.forEach(client -> {
         if(client == session) {
            return;
         }
         try {
            session.getBasicRemote().sendText(msg);
         } catch (IOException e) {
            e.printStackTrace();
         }
      });*/
      
      for (Session s : clients) {
         System.out.println("send data : " + msg);
         s.getBasicRemote().sendText(msg);
      }
      
   }
   
   @OnOpen
   public void onOpen(Session s) {
      System.out.println("session open : " + s);
      if(!clients.contains(s)) {
         clients.add(s);
         System.out.println("session open : " + s);
      }else {
         System.out.println("This Session Data has been Already Connected!");
      }
   }
   
   @OnClose
   public static void onClose(Session s) {
      System.out.println("session close : " + s);
      clients.remove(s);
   }
   @OnError
   public void onError(Throwable t) throws Exception{
      System.out.println(t.toString());
   }
}