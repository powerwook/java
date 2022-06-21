package com.cloudrip.config.oauth;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cloudrip.domain.User;
import com.cloudrip.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	@Lazy
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			System.out.println(oAuth2User.getAttributes());
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map<String, Object>)oAuth2User.getAttributes().get("response"));
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			System.out.println("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
		}else {
			System.out.println("우리는 구글과 네이버와 페이스북만 지원해요");
		}
		System.out.println("PrincipalOauth2UserService");
		String provider = oAuth2UserInfo.getProvider();
		String providerId = provider+"_"+oAuth2UserInfo.getProviderId();
		String username = oAuth2UserInfo.getName();
		String password = bCryptPasswordEncoder.encode("겟인데어"); 
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		User userEntity = userRepository.findByProviderId(providerId);
		System.out.println(userEntity);
		int randomint = (int) (Math.random()*100000);
		String random =String.valueOf(randomint);
		if(userEntity==null) {
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.roles(role)
					.provider(provider)
					.nickname(username+random)
					.userRegdate(LocalDate.now())
					.provider_id(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("당신은 이미 로그인을 한 적이 있습니다.");
			
		}
		return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
	}
	
	
	
}
