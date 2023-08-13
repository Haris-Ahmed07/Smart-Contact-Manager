package com.smart.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.smart.entities.User;


@Controller
class MainController {
	

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home  -Smart_Contact_Manager");
		return "home";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Signup  -Smart_Contact_Manager");
		model.addAttribute("user", new User());

		return "signup";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "Login  -Smart_Contact_Manager");
		return "login";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About  -Smart_Contact_Manager");
		return "about";
	}


}
