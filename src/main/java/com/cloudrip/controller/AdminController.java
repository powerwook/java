package com.cloudrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/add")
	public String add() {
		return "add";
	}
	
//	@GetMapping("/list")
//	public String list() {
//		return "list";
//	}
}
