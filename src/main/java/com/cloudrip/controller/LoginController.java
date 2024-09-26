package com.cloudrip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.SignoutForm;
import com.cloudrip.domain.User;
import com.cloudrip.service.UserService;

@Controller
public class LoginController {

	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		try {
			User user = principalDetails.getUser();
		} catch (Exception e) {
			return "login";
		}
		
		return "againLoginAlert";
	}
	
	@PostMapping("/signout")
	public String signout(@RequestBody String signout) {
		signout = signout.replace("providerId=","");
		userService.deleteUser(signout);
		return "redirect:/logout";
	}
}
