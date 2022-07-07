package com.cloudrip.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.Board;
import com.cloudrip.domain.Image;
import com.cloudrip.domain.Review;
import com.cloudrip.domain.SignoutForm;
import com.cloudrip.domain.User;
import com.cloudrip.dto.ImageUploadDto;
import com.cloudrip.repository.UserRepository;
import com.cloudrip.service.BoardService;
import com.cloudrip.service.ImageService;
import com.cloudrip.service.ReviewService;
import com.cloudrip.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("")
	public String mypageGet(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetails userDetails = (PrincipalDetails) principal;
		User user = userDetails.getUser();
		Image image=user.getImage();
		model.addAttribute("user",user);
		model.addAttribute("image",image);
		return "mypage";
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public String mypagePost(Model model,@RequestBody(required=false) String nickname, User user) {
		System.out.println(nickname);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			user = objectMapper.readValue(nickname, user.getClass());
			User newUser = userService.updateUserNickname(user.getNickname());
			model.addAttribute("user",newUser);
			return "mypage";
		}catch(IOException e) {
			e.printStackTrace();
			return "mypage";
		}
	}
	
	@RequestMapping(value="/image",method=RequestMethod.POST)
	public ModelAndView mypage(
			@RequestParam(value="file",required=false) MultipartFile multipartFile,ImageUploadDto imageUploadDto, ModelAndView modelAndView) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetails userDetails = (PrincipalDetails) principal;
		imageService.ImageUpload(imageUploadDto, userDetails,multipartFile);
		modelAndView.setViewName("redirect:/mypage");
		return modelAndView;
	}
	
	@GetMapping("/review")
	public String mypageReview(Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		User user =  principalDetails.getUser();
		User user2 = userService.findByProviderId(principalDetails.getUser().getProviderId());
		
		model.addAttribute("reviews",user2.getReviews());
		return "review";
	}
	
	@RequestMapping(value="/review" , method=RequestMethod.POST)
	public String mypageReviewPost(@RequestParam Long boardId,
			String boardTitle,String boardSubtitle1,String boardSubtitle2
			) {
		Board board = boardService.createBoard(boardId,boardTitle,boardSubtitle1,boardSubtitle2);
		reviewService.create(board);
		return "review";
	}
	
	
	@PostMapping("/review/deleteAll")
	public String reviewDeleteAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		reviewService.deleteReviewAll(principalDetails);
		return "redirect:/mypage/review";
		
	}
	
	
	@PostMapping("/filter")
	public String reviewDeleteAll() {
		MypageController mypageController = new MypageController();
		List<Review> reviewList =reviewService.findAll();
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
			result=mypageController.sendJSON(bellInJsonObject);
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		org.json.JSONObject outputJObject = new org.json.JSONObject(result);
		JSONArray outputJArray = outputJObject.getJSONArray("bell_out");
		System.out.println("============================");
		for(int i=0; i<outputJArray.length(); i++) {
			org.json.JSONObject obj = outputJArray.getJSONObject(i);
			int label= obj.getInt("label");
			int id = obj.getInt("id");
			reviewService.updateFilter(new Long(id),new Long(label));
		}
		return "redirect:/mypage";
	}
	
	
	 public String sendJSON(JSONObject jObject) throws IOException{
	        String inputLine=null;
	        StringBuffer stringBuffer=new StringBuffer();

	        URL url=new URL("http://3.39.179.14:8000/"); //URL객체 생성
	            
	        HttpURLConnection conn=(HttpURLConnection)url.openConnection(); //url주소를 가지고 Http 커넥션 객체 생성
	            
	        System.out.println(conn.toString());
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("accept", "application/json");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setConnectTimeout(10000);
	        conn.setReadTimeout(10000);
	            
	        //데이터 전송
	        BufferedWriter bWriter=new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
//	        데이터쓰기
	        bWriter.write(jObject.toString());
//	        데이터 보내기
	        bWriter.flush();
	        //전송된 결과를 읽어옴
	        BufferedReader bReader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	        while((inputLine=bReader.readLine())!=null){
	            stringBuffer.append(inputLine);
	        }
	        bWriter.close();
	        bReader.close();
	        conn.disconnect();
// 			전송된 결과 반환
	        return stringBuffer.toString();
	    }//sendJSON()
	
	
}
