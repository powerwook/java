package com.cloudrip.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cloudrip.config.oauth.PrincipalDetails;
import com.cloudrip.domain.User;

@Controller
public class MainController {

	@GetMapping("/")
	public String index(Model model) {
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return "home";
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PrincipalDetails userDetails = (PrincipalDetails) principal;
		User user = userDetails.getUser();
		model.addAttribute("user",user);
		return "home";
	}
}
