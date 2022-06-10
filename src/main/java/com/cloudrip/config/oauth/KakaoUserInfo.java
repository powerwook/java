package com.cloudrip.config.oauth;
import java.util.Map;
public class KakaoUserInfo implements OAuth2UserInfo{
	
	private Map<String, Object> attributes; // oauth2User.getAttributes()
	
//	private Map<String, Object> kakaoData = (Map<String, Object>) attributes.get("kakao_account");
//	private Map<String, Object> profileData = (Map<String, Object>) kakaoData.get("profile");
	
	
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		String providerId = String.valueOf(attributes.get("id"));
		return providerId;
	}
	@Override
	public String getProvider() {
		return "kakao";
	}
	@Override
	public String getEmail() {
		// String email = ((Map)attributes.get("kakao_acount")).get("email").toString();
		// String email = String.valueOf(kakaoData.get("email"));
		// System.out.println("attributes().get('id') : " + String.valueOf(attributes.get("id")));
		// System.out.println((Map)attributes.get("kakao_account"));
		String email = String.valueOf(((Map)attributes.get("kakao_account")).get("email"));
		
		return email;
	}
	@Override
	public String getName() {
		// String name = String.valueOf(profileData.get("nickname"));
		// Map<Object, String> kakao_account = (Map)attributes.get("kakao_acount");
		String name = ((Map)((Map)attributes.get("kakao_account")).get("profile")).get("nickname").toString();
		return name;
	}
}