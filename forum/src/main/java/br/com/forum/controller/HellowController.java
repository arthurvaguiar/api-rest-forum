package br.com.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HellowController {
	
	@RequestMapping("/")
	@ResponseBody
	public String hellow() {
		return "olá mundo";
	}

}
