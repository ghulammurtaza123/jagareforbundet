package com.produkt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.produkt.helper.Message;
import com.produkt.model.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.produkt.dao.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@Autowired
	UserRepository repo;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home-Jägareförbundet Stockholm");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Jägareförbundet Stockholm");
		return "about";
	}

	@RequestMapping("/register")
	public String Register(Model model) {
		model.addAttribute("title", "Register-Jägareförbundet Stockholm");
		model.addAttribute("user", new User());
		return "register";
	}

	// handler for registration

	@PostMapping("/process")
	public String registraionHandler(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {
			if (!agreement) {
				System.out.println("You have not accepted terms and conditions");
				throw new Exception("Acceptera villkoren");
			}

			if (result.hasErrors()) {
				
				System.out.println("List of errors" + result.getAllErrors());
				model.addAttribute("user", user);
				System.out.println("This is the error with following user" + user);
				return "register";

			} else {
				String userEmail = user.getEmail();
				System.out.println("userEmail: "+ userEmail);
				if (userEmail.endsWith("@jagareforbundet.se")) {
	                user.setRole("ROLE_ADMIN");
	            } else {
	                user.setRole("ROLE_USER");
	            }
				user.setEnabled(true);
				// user.setImageUrl("default.png");
				user.setPassword(passEncoder.encode(user.getPassword()));
				System.out.println("agreement " + agreement);
				System.out.println("user " + user);

				repo.save(user);

				model.addAttribute("user", new User());
				session.setAttribute("message", new Message("Succefully Registered !!", "alert-success"));

				return "redirect:/signin";
			}
		} catch (Exception e) {

			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Något gick fel!!" + e.getMessage(), "alert-danger"));

			return "register";
		}

	}

	@RequestMapping("/signin")
	public String customLogin(Model model) {

		model.addAttribute("title", "login - Jägareförbundet Stockholm");
		// model.addAttribute("user",new User());

		return "login";
	}
}