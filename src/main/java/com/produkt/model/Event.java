
package com.produkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Event")
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int eId;
	
	@NotBlank(message = "Fyll i det här fältet !!")
	private String rubrik;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String weblink;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String category;
	private String datepicker;
	private String krets;
	private String plats;

	@ManyToOne
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "krets_id")
	private Krets kret;

	public int geteId() {
		return eId;
	}

	public void seteId(int eId) {
		this.eId = eId;
	}

	public String getRubrik() {
		return rubrik;
	}

	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDatepicker() {
		return datepicker;
	}

	public void setDatepicker(String datepicker) {
		this.datepicker = datepicker;
	}

	public String getKrets() {
		return krets;
	}

	public void setKrets(String krets) {
		this.krets = krets;
	}

	public String getPlats() {
		return plats;
	}

	public void setPlats(String plats) {
		this.plats = plats;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}
	
	

	public Krets getKret() {
		return kret;
	}

	public void setKret(Krets kret) {
		this.kret = kret;
	}



}
