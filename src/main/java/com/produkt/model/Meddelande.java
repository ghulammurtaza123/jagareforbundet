package com.produkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meddelande")
public class Meddelande {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String rubrik;
    
    private String datepicker;
    
    private String krets;
    
    private String category;
    
    private String weblink;
    
    private String imageUrl;
    
    @Column(length = 1000)
    private String message;
    

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

   
    
    
	public Meddelande() {
		
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public String getRubrik() {
		return rubrik;
	}



	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}



	public String getKrets() {
		return krets;
	}



	public void setKrets(String krets) {
		this.krets = krets;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}



	public String getDatepicker() {
		return datepicker;
	}



	public void setDatepicker(String datepicker) {
		this.datepicker = datepicker;
	}
	
	

		
	
    
    
}
