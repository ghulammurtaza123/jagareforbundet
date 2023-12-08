/*
 * package com.produkt.controller;
 * 
 * import org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import com.produkt.model.Rating;
 * 
 * @Controller public class RatingController {
 * 
 * @GetMapping("/rate") public String showRatingForm(Model model) {
 * model.addAttribute("rating", new Rating());// Set the initial value of stars
 * return "rate/index"; }
 * 
 * // Handle form submission if needed
 * 
 * @PostMapping("/submit") public String
 * processRatingForm(@ModelAttribute("rating") Rating rating) { // Process the
 * submitted rating System.out.println("Stars: " + rating.getStars()); // Add
 * your logic here return "redirect:/user/index"; } }
 */