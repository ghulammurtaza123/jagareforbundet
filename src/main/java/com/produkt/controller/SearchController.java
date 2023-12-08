
package com.produkt.controller;

import com.produkt.model.News;
import com.produkt.model.User;
import com.produkt.model.Event;
import com.produkt.dao.EventRepository;
import com.produkt.dao.NewsRepository;
import com.produkt.dao.UserRepository;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchController {
    
    @Autowired
    private NewsRepository newsRepo;
    
    @Autowired
    private EventRepository eventRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/search/{query}")
    public ResponseEntity<?>  SearchContacts(@PathVariable("query") String query,Principal p){
        
        User user = userRepo.getUserByEmail(p.getName());
        
        System.out.println("query: " + query );
        System.out.println("user: " + user );
    
       List<News> news= newsRepo.findByRubrikContainingAndUser(query, user);
       
       return ResponseEntity.ok(news);
        
    }
    
    @GetMapping("/searches/{query1}")
    public ResponseEntity<?>  SearchNews(@PathVariable("query1") String query,Principal p){
     
    	List<News> news  = newsRepo.findByRubrikContaining(query);
    	
           return ResponseEntity.ok(news);
        
    }
    
    
    @GetMapping("/search/event/{query}")
    public ResponseEntity<?>  SearchEvents(@PathVariable("query") String query,Principal p){
        
        User user = userRepo.getUserByEmail(p.getName());
    
       List<Event> event= eventRepo.findByRubrikContainingAndUser(query, user);
       
       return ResponseEntity.ok(event);
        
    }
    
    @GetMapping("/searches/event/{query1}")
    public ResponseEntity<?>  SearchEvent(@PathVariable("query1") String query,Principal p){
     
    	List<Event> event  = eventRepo.findByRubrikContaining(query);
    	
           return ResponseEntity.ok(event);
        
    }
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
                           
    
   
    
}
