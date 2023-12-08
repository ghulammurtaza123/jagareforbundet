package com.produkt.controller;

import com.produkt.model.User;
import com.produkt.service.EmailService;
import com.produkt.dao.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

	Random rand = new Random(1000);

	@Autowired
	private EmailService emailServ;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// form open handler
	@GetMapping("/forgot_password")
	public String emailForm() {

		return "forget_email_form";
	}

	// form open handler

	@PostMapping("/send-otp")
	public String emailFormSubmission(@RequestParam("email") String email,
			jakarta.servlet.http.HttpSession httpSession) {

		LocalDateTime currentDateTime = LocalDateTime.now();

		// Define a format for the date and time string
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddHHmmss");

		// Format the current date and time using the defined format
		String formattedDateTime = currentDateTime.format(formatter);

		// Print the formatted date and time string
		// System.out.println("Current Date and Time: " + formattedDateTime);

		int otp = rand.nextInt(Integer.parseInt(formattedDateTime));

		String subject = " OTP from Jagareförbundet Stockholm";
		String message = " " + "<div style='border:1px solid #28A1B9; padding:20px'>" + "<h1>" + "OTP är" + "<b> " + otp
				+ "</n>" + "</h1> " + "</div>";

		boolean flag = emailServ.sendEmail(subject, message, email);

		if (flag) {

			httpSession.setAttribute("actualOtp", otp);
			httpSession.setAttribute("email", email);
			return "verify_otp";

		} else {
			httpSession.setAttribute("message", "Kontrollera ditt e-post-ID!!");
			return "forget_email_form";
		}

	}

	@PostMapping("/verify-otp")
	public String verifyOTP(@RequestParam("otp") Integer otp, jakarta.servlet.http.HttpSession session) {

		Integer actual_otp = (Integer) session.getAttribute("actualOtp");
		String email = (String) session.getAttribute("email");

		if (actual_otp.equals(otp)) {

			User user = userRepo.getUserByEmail(email);

			if (user == null) {

				session.setAttribute("message", "Användaren finns inte med denna e-post !!");
				return "forget_email_form";
			} else {

			}

			return "password_change_form";
		} else {
			session.setAttribute("message", "Du har angett fel OTP !!");
			return "verify_otp";
		}

	}

	// Change password module

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("changePass") String newPass, jakarta.servlet.http.HttpSession session) {

		String email = (String) session.getAttribute("email");
		User user = userRepo.getUserByEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(newPass));
		userRepo.save(user);
		

		return "redirect:/signin?change= Lösenordet har ändrats..";

	}

}
