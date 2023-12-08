package com.produkt.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.produkt.dao.EventRepository;
import com.produkt.dao.KretsRepository;
import com.produkt.dao.MeddelandeRepository;
import com.produkt.dao.NewsRepository;
import com.produkt.dao.RatingRepository;
import com.produkt.dao.UserKretsRepository;
import com.produkt.dao.UserRepository;
import com.produkt.model.Event;
import com.produkt.model.Krets;
import com.produkt.model.Meddelande;
import com.produkt.model.News;
import com.produkt.model.Rating;
import com.produkt.model.User;
import com.produkt.model.UserKrets;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	MeddelandeRepository meddelande_repo;

	@Autowired
	KretsRepository krets_repo;

	@Autowired
	UserKretsRepository userkrets_repo;

	@Autowired
	RatingRepository rating_repo;

	@Autowired
	NewsRepository news_repo;

	@Autowired
	EventRepository event_repo;

	@Autowired
	UserRepository repo;

	@Autowired
	BCryptPasswordEncoder bCryptPass;

	private final RestTemplate restTemplate;

	@Autowired
	public UserController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@ModelAttribute
	public void helperForRepeatingCode(Model m, Principal p) {

		User user = repo.getUserByEmail(p.getName());
		m.addAttribute("user", user);

	}

	@GetMapping("/index")
	public String dashboard(Model model, Principal princip) {

		model.addAttribute("title", " ⛪ User -Jägareförbundet Stockholm");

		return "user/index";
	}

	@GetMapping("/profil")
	public String profile(Model model, Principal princip) {

		model.addAttribute("title", " ⛪ Profil -Jägareförbundet Stockholm");

		return "user/profile";
	}

	// Show News handler
	@GetMapping("/home/{page}")
	public String showNews(@PathVariable("page") Integer page, @ModelAttribute("krets") Krets krets, Model model,
			Principal p) {

		model.addAttribute("title", " 🕵️‍♀️ User News   ");
		Meddelande lastMeddelande;
		User user = repo.getUserByEmail(p.getName());
		List<Meddelande> meddelandeList = meddelande_repo.findByKrets(user.getKrets());
		Integer userRatings = (Integer) model.asMap().get("userRatings");

		if (!meddelandeList.isEmpty()) {
			int lastIndex = meddelandeList.size() - 1;
			lastMeddelande = meddelandeList.get(lastIndex);
			
		} else {
			lastMeddelande = new Meddelande();

			System.out.println("Ingen Meddelande hittad för angivna krets.");
		}

		Pageable pageable = PageRequest.of(page, 9);
		// Page<News> news = news_repo.findNewsByUser(user.getId(), pageable);
		Page<News> news = news_repo.findAll(pageable);
		
		List<UserKrets> userKrets = userkrets_repo.findKretsByUserId(user.getId());

		List<String> selectedKrets = userKrets.stream().map(UserKrets::getKrets).map(Krets::getKretName).distinct()
				.collect(Collectors.toList());
			 List<News> relevantNews = new ArrayList<>();
		 List<Event> relevantEvent = new ArrayList<>();
		    if (selectedKrets != null && !selectedKrets.isEmpty()) {
		        List<Krets> selectedKretsEntities = krets_repo.findByKretNameIn(selectedKrets);
		      
		        for (Krets kret : selectedKretsEntities) {
		            List<News> newsForKrets = news_repo.findByKret(kret);
		            List<Event> eventForKrets = event_repo.findByKret(kret);
		          
		            relevantNews.addAll(newsForKrets);
		            relevantEvent.addAll(eventForKrets);
		        }
		    }

		model.addAttribute("meddelande", lastMeddelande);
		model.addAttribute("isEmptyMeddelandeList", meddelandeList.isEmpty());
		model.addAttribute("news", relevantNews);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", news.getTotalPages());
		model.addAttribute("rating", new Rating());// Set the initial value of stars
		
		/*
		 * for (News newsItem : relevantNews) { // Assuming there's a method to get the
		 * last rating for a news item Rating lastRating = newsItem.getLastRating();
		 * 
		 * if (lastRating != null) { int stars = lastRating.getStars();
		 * System.out.println("News ID: " + newsItem.getnId() + ", Last Rating Stars: "
		 * + stars); } else { System.out.println("News ID: " + newsItem.getnId() +
		 * ", No Ratings Yet"); } }
		 */


		List<Event> event = event_repo.findAll();
		String months = null, time = null;
		int day = 0;
		List<String> monthsList = new ArrayList<>();
		List<Integer> dayList = new ArrayList<>();
		List<String> timeList = new ArrayList<>();
		for (Event event_item : relevantEvent) {
			String str = event_item.getDatepicker();

			try {
				// Define the formatter for the specified date-time pattern
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

				// Parse the string to obtain a LocalDateTime object
				LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

				// Extract month, day, and time
				Month month = dateTime.getMonth();
				day = dateTime.getDayOfMonth();
				months = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
				time = dateTime.toLocalTime().toString();

				monthsList.add(months);
				dayList.add(day);
				timeList.add(time);

			} catch (Exception e) {
				// Handle the case where the string is not a valid date-time
				System.err.println("Error: The string is not a valid date-time.");
			}

		}

		model.addAttribute("events", relevantEvent);
		model.addAttribute("month", monthsList);
		model.addAttribute("day", dayList);
		model.addAttribute("time", timeList);

	;

				// Fetch all available Krets from the database
		List<Krets> allKrets = krets_repo.findAll();

		// Pass the sets of selected and all Krets to the view
		model.addAttribute("selectedKrets", selectedKrets);
		model.addAttribute("allKrets", allKrets);
		/* model.addAttribute("krets", new Krets()); */

		return "user/index";
	}

	@PostMapping("/home/{page}")
	public String processRatingForm(@PathVariable("page") Integer page, @ModelAttribute("rating") Rating rating,
			@RequestParam(value = "newsId", required = false) Integer newsId,
			@RequestParam(value = "krets", required = false) List<String> selectedKrets, Principal p, Model model,
			RedirectAttributes redirectAttributes) {

		// Process the submitted rating
		User user = repo.getUserByEmail(p.getName());
		String pages = page.toString();
		if (newsId != null) {
			News news = news_repo.findById(newsId).get();
			
			rating.setUser(user);
			rating.setNews(news);
			rating_repo.save(rating);

			List<Integer> userRating = rating_repo.findRatingByNewsIdAndUserId(newsId, user.getId());

			Integer Rating = userRating.get(userRating.size() - 1);
			redirectAttributes.addFlashAttribute("userRatings", Rating);
			redirectAttributes.addFlashAttribute("newsId", newsId);
			// redirectAttributes.addFlashAttribute("latestRatings", latestRatings);
			model.addAttribute("userRatings", Rating);
		} else {
			// Clear existing userKrets to avoid duplicates
			user.getUserKrets().clear();
			if (user != null && selectedKrets != null) {

				
				// Create UserKrets entities for the selected krets and associate them with the
				// user
				for (String kretsName : selectedKrets) {

					// Retrieve the Krets entity based on kretsName
					Krets krets = krets_repo.findByKretName(kretsName);
					if (krets == null) {
						krets = new Krets();
						krets.setKretName(kretsName);
						krets_repo.save(krets);
					}

					UserKrets userKrets = new UserKrets();
					userKrets.setUser(user);
					userKrets.setKrets(krets);
					user.getUserKrets().add(userKrets);
				}
			}
			
			List<UserKrets> existingUserKrets = userkrets_repo.findKretsByUserId(user.getId());
		    for (UserKrets existingUserKret : existingUserKrets) {
		        if (selectedKrets != null && !selectedKrets.contains(existingUserKret.getKrets().getKretName())) {
		            userkrets_repo.delete(existingUserKret);
		        }
		    }

			// Save the updated user entity
			repo.save(user);
		}

		// Add your logic here
		return "redirect:/user/home/" + pages;
	}

	/*
	 * @GetMapping("/{nId}") public String showNewsDetails(@PathVariable Integer
	 * nId, Model model, Principal principal) { // Retrieve the user's previous
	 * rating from the database
	 * 
	 * 
	 * // Other logic for displaying news details...
	 * 
	 * return "news_details_template"; }
	 */

//  showing specific news details
	@GetMapping("/{nId}/news")
	public String getspecificContatct(@PathVariable("nId") Integer newsId, Model model, Principal p) {

		News news = news_repo.findById(newsId).get();

		// String userName = p.getName();

		// User user = repo.getUserByEmail(userName);
		// if (user.getId() == news.getUser().getId()) {
		model.addAttribute("newsDetails", news);
		model.addAttribute("title", news.getRubrik());
		// }

		return "user/news_details";
	}

}
