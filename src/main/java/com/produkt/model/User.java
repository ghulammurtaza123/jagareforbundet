package com.produkt.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@NoArgsConstructor

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String name;
	@Column(unique = true)
	@NotBlank(message = "Fyll i det här fältet !!")
	private String email;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String password;
	private String role;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String krets;
	private boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<News> news;



	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Event> event;
	
	  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	   private List<UserKrets> userKrets;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getKrets() {
		return krets;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public void setKrets(String krets) {
		this.krets = krets;
	}
	
	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}

	public List<UserKrets> getUserKrets() {
		return userKrets;
	}

	public void setUserKrets(List<UserKrets> userKrets) {
		this.userKrets = userKrets;
	}

	
	

	
}
