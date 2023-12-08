package com.produkt.controller;

import com.produkt.model.Event;
import com.produkt.model.Krets;
import com.produkt.model.Meddelande;
import com.produkt.model.News;
import com.produkt.model.User;
import com.produkt.dao.EventRepository;
import com.produkt.dao.KretsRepository;
import com.produkt.dao.MeddelandeRepository;
import com.produkt.dao.NewsRepository;
import com.produkt.dao.RatingRepository;
import com.produkt.dao.UserRepository;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.produkt.helper.Message;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	NewsRepository news_repo;
	
	@Autowired
	KretsRepository krets_repo;
	
	@Autowired
	MeddelandeRepository meddelande_repo;
	
	@Autowired
	RatingRepository rating_repo;

	@Autowired
	EventRepository event_repo;

	@Autowired
	UserRepository repo;

	@Autowired
	BCryptPasswordEncoder bCryptPass;

	@ModelAttribute
	public void helperForRepeatingCode(Model m, Principal p) {

		User user = repo.getUserByEmail(p.getName());
		m.addAttribute("user", user);

	}

	@GetMapping("/index")
	public String dashboard(Model model, Principal p) {

		model.addAttribute("title", " ⛪ Admin-Jägareförbundet Stockholm");
		User user = repo.getUserByEmail(p.getName());
		
		
		 int numberOfUsersWithSameKrets = repo.findByKrets(user.getKrets()).size();
		 int numberOfUsersWithSameKretsAndRole  = repo.findByKretsAndRole(user.getKrets(),"ROLE_USER").size();
		 int rating_5=rating_repo.countStarsWithRatingExcellent();
		 int rating_4=rating_repo.countStarsWithRatingGood();
		 int rating_3=rating_repo.countStarsWithRatingInteresting();
		 int rating_2=rating_repo.countStarsWithRatingAverage();		 
		 int rating_1=rating_repo.countStarsWithRatingBad();
		 
		
		 model.addAttribute("number_of_users", numberOfUsersWithSameKrets);
		 model.addAttribute("number_of_users_with_users_role", numberOfUsersWithSameKretsAndRole);
		 model.addAttribute("rating_5", rating_5);
		 model.addAttribute("rating_4", rating_4);
		 model.addAttribute("rating_3", rating_3);
		 model.addAttribute("rating_2", rating_2);
		 model.addAttribute("rating_1", rating_1);
		return "admin/index";
	}

	@GetMapping("/test")
	public String dashboardtest(Model model) {

		model.addAttribute("title", " ⛪ Admin-Jägareförbundet Stockholm");

		return "admin/test";
	}

	@RequestMapping("/add_news")
	public String addContacts(Model model) {

		model.addAttribute("title", " ✏  Lägg Nyheter   ");
		model.addAttribute("news", new News());
		return "/admin/add_nyheter";

	}
	
	@RequestMapping("/add_meddelande")
	public String addMessage(Model model) {

		model.addAttribute("title", " ✏  Lägg Meddelande   ");
		model.addAttribute("meddelande", new Meddelande());
		return "/admin/add_message";

	}
	

//  proceesing meddelande form
	@PostMapping("/process-meddelande")
	public String postMeddelande(@ModelAttribute("meddelande") Meddelande meddelande, @RequestParam("profileImage") MultipartFile file,
			jakarta.servlet.http.HttpSession session, Principal p) {

		try {
			User user = repo.getUserByEmail(p.getName());

			if (file.isEmpty()) {
				System.out.println("No image uploaded!");
				meddelande.setImageUrl("blank.png");
			} else {
				meddelande.setImageUrl(file.getOriginalFilename());
				File fileObject = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(fileObject.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}

			/* user.get.add(news); */
			meddelande.setUser(user);
			meddelande_repo.save(meddelande);
			System.out.println(meddelande);
			session.setAttribute("message", new Message("Meddelande tillagd! Lägga till flera...", "success"));

		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Något gick fel", "danger"));
		}

		return "admin/add_message";
	}
	
	

//    proceesing nyhet form
	@PostMapping("/process-nyhet")
	public String postContact(@ModelAttribute("news") News news, @RequestParam("profileImage") MultipartFile file,
			jakarta.servlet.http.HttpSession session, Principal p) {

		try {
			User user = repo.getUserByEmail(p.getName());

			if (file.isEmpty()) {
				System.out.println("No image uploaded!");
				news.setImageUrl("blank.png");
			} else {
				news.setImageUrl(file.getOriginalFilename());
				File fileObject = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(fileObject.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}
			Krets krets = krets_repo.findByKretName(news.getKrets());
			if (krets == null) {
			    krets = new Krets();
			    krets.setKretName(news.getKrets());
			    krets_repo.save(krets);
			}

			news.setKret(krets);
			user.getNews().add(news);
			news.setUser(user);
			news_repo.save(news); // Save the News entity, JPA will manage the relationship

			// You might also want to update the user's news list if necessary
			user.getNews().add(news);
			repo.save(user);
						session.setAttribute("message", new Message("Nyheter tillagd! Lägga till flera...", "success"));

		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Något gick fel", "danger"));
		}

		return "admin/add_nyheter";
	}

//Show News handler
	@GetMapping("/show_news/{page}")
	public String showNews(@PathVariable("page") Integer page, Model model, Principal p) {

		model.addAttribute("title", " 🕵️‍♀️ Admin News   ");
		User user = repo.getUserByEmail(p.getName());

		Pageable pageable = PageRequest.of(page, 6);
		Page<News> news = news_repo.findNewsByUser(user.getId(), pageable);

		model.addAttribute("news", news);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", news.getTotalPages());
		return "admin/show_news";
	}

//  showing specific news details
	@GetMapping("/{nId}/news")
	public String getspecificContatct(@PathVariable("nId") Integer newsId, Model model, Principal p) {

		News news = news_repo.findById(newsId).get();

		String userName = p.getName();

		User user = repo.getUserByEmail(userName);
		if (user.getId() == news.getUser().getId()) {
			model.addAttribute("newsDetails", news);
			model.addAttribute("title", news.getRubrik());
		}

		return "admin/news_details";
	}

//delete news handler
	@GetMapping("/delete/{nId}")
	public String deleteNews(@PathVariable("nId") Integer nId, Principal p) {

		News news = news_repo.findById(nId).get();

		String userName = p.getName();

		User user = repo.getUserByEmail(userName);
		if (user.getId() == news.getUser().getId()) {
			news_repo.deleteById(nId);
		}
		return "redirect:/admin/show_news/0";
	}

//open update form handler
	@GetMapping("/update-news/{nId}")
	public String updateForm(@PathVariable("nId") Integer nId, Model m, Principal p) {

		News news = news_repo.findById(nId).get();
		String userName = p.getName();

		User user = repo.getUserByEmail(userName);
		if (user.getId() == news.getUser().getId()) {

			m.addAttribute("title", "update-news");
			m.addAttribute("news", news);

		}

		return "admin/update_form";
	}

//update news
	@PostMapping("/process-update")
	public String updateNews(@ModelAttribute("news") News news, @RequestParam("profileImage") MultipartFile file,
			jakarta.servlet.http.HttpSession session, Principal p) {

		try {
			User user = repo.getUserByEmail(p.getName());
			News oldNews = news_repo.findById(news.getnId()).get();
			if (file.isEmpty()) {

				news.setImageUrl(oldNews.getImageUrl());
			} else {

//          delete old image
				File oldFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(oldFile, oldNews.getImageUrl());
				file1.delete();

//          Update image
				news.setImageUrl(file.getOriginalFilename());
				File fileObject = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(fileObject.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}

			Krets krets = krets_repo.findByKretName(news.getKrets());
			if (krets == null) {
			    krets = new Krets();
			    krets.setKretName(news.getKrets());
			    krets_repo.save(krets);
			}

			news.setKret(krets);
			news.setUser(user);
			news_repo.save(news);
			session.setAttribute("message", new Message("news is updated!", "success"));

		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong", "danger"));
		}

		return "redirect:/admin/" + news.getnId() + "/news";
	}

	@RequestMapping("/event")
	public String addEvent(Model model) {

		model.addAttribute("title", " ✏  Lägg Evenet");
		model.addAttribute("event", new Event());
		return "/admin/event";

	}

//processing event form
	@PostMapping("/process-event")
	public String postContact(@ModelAttribute("event") Event event, jakarta.servlet.http.HttpSession session,
			Principal p) {

		try {
			User user = repo.getUserByEmail(p.getName());

			System.out.println(event.getDatepicker());
			
			Krets krets = krets_repo.findByKretName(event.getKrets());
			if (krets == null) {
			    krets = new Krets();
			    krets.setKretName(event.getKrets());
			    krets_repo.save(krets);
			}

			event.setKret(krets);

			user.getEvent().add(event);
			event.setUser(user);
			repo.save(user);
			System.out.println(event);
			session.setAttribute("message", new Message("Event tillagd! Lägg till flera...", "success"));

		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Något gick fel", "danger"));
		}

		return "redirect:/admin/event";
	}

	// Show News handler
	@GetMapping("/show_event")
	public String showEvent(Model model, Principal p) {

		model.addAttribute("title", " 🕵️‍♀️ Admin Event   ");
		User user = repo.getUserByEmail(p.getName());

		List<Event> event = event_repo.findEventByUser(user.getId());

		model.addAttribute("event", event);

		return "admin/show_event";
	}
	
	//delete event handler
		@GetMapping("/delete/event/{eId}")
		public String deleteEvent(@PathVariable("eId") Integer eId, Principal p) {

			Event event= event_repo.findById(eId).get();

			String userName = p.getName();

			User user = repo.getUserByEmail(userName);
			if (user.getId() == event.getUser().getId()) {
				event_repo.deleteById(eId);
			}
			return "redirect:/admin/show_event";
		}
		
		//open update form handler
		@GetMapping("/update-event/{eId}")
		public String updateEventForm(@PathVariable("eId") Integer eId, Model m, Principal p) {

			Event event = event_repo.findById(eId).get();
			String userName = p.getName();

			User user = repo.getUserByEmail(userName);
			if (user.getId() == event.getUser().getId()) {

				m.addAttribute("title", "update-event");
				m.addAttribute("event", event);

			}

			return "admin/update_event_form";
		}

	//update event
		@PostMapping("/process-event-update")
		public String updateEvent(@ModelAttribute("event") Event event,
				jakarta.servlet.http.HttpSession session, Principal p) {

			try {
				User user = repo.getUserByEmail(p.getName());
				
				//user.getEvent().add(oldEvent);
				// user.getContacts().add(news);
				Krets krets = krets_repo.findByKretName(event.getKrets());
				if (krets == null) {
				    krets = new Krets();
				    krets.setKretName(event.getKrets());
				    krets_repo.save(krets);
				}

				event.setKret(krets);
				event.setUser(user);
				event_repo.save(event);
				session.setAttribute("message", new Message("Event Uppdterad!", "success"));

			} catch (Exception e) {
				System.out.println("Error :" + e.getMessage());
				e.printStackTrace();
				session.setAttribute("message", new Message("Något gick fel", "danger"));
			}

			return "redirect:/admin/show_event";
		}
		
		// user profile handler
		    @GetMapping("/profile")
		    public String userProfile(Model m) {

		        m.addAttribute("title", "Admin Profil ");

		        return "admin/profile";

		    }

		    //settings handler
		    @GetMapping("/settings")
		    public String userSettings(Model m) {

		        m.addAttribute("title", "Settings");

		        return "admin/settings";

		    }

		    // change password handler
		    @PostMapping("/change-password")
		    public String changePassword(@RequestParam("oldPassword") String oldPass,
		            @RequestParam("newPassword") String newPass,
		            @RequestParam("repeatNewPassword") String repPass, Model m, Principal p,
		            jakarta.servlet.http.HttpSession httpSession) {

		        m.addAttribute("title", "Settings");

		        User user = repo.getUserByEmail(p.getName());

		        String oldPassword = user.getPassword();
		        if (bCryptPass.matches(oldPass, oldPassword)) {
		            if (newPass.equals(repPass)) {
		                user.setPassword(bCryptPass.encode(newPass));
		                repo.save(user);
		                httpSession.setAttribute("message", new Message("Lösenord uppdaterad!", "success"));
		            } else {
		                httpSession.setAttribute("message", new Message("Gammalt och nytt lösenord matchar inte."
		                		+ "Försök igen!", "danger"));
		                return "redirect:/admin/settings";
		            }
		        } else {

		            httpSession.setAttribute("message", new Message("Fel lösenord!", "danger"));

		            return "redirect:/admin/settings";
		        }

		        return "redirect:/admin/index";

		    }
		    
		//  showing specific search event details
			@GetMapping("/{eId}/event")
			public String getspecificevent(@PathVariable("eId") Integer eventId, Model model, Principal p) {

				Event event = event_repo.findById(eventId).get();

				String userName = p.getName();

				User user = repo.getUserByEmail(userName);
				if (user.getId() == event.getUser().getId()) {
					model.addAttribute("eventDetails", event);
					model.addAttribute("title", event.getRubrik());
				}

				return "admin/show_event_search";
			}

}
