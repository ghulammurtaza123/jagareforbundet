
package com.produkt.model;

import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "News")
@NoArgsConstructor
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int nId;
	@Column(unique = true)
	@NotBlank(message = "Fyll i det här fältet !!")
	private String rubrik;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String weblink;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String category;
	@NotBlank(message = "Fyll i det här fältet !!")
	private String datepicker;
	private String krets;
	private String imageUrl;

	@Column(length = 1000)
	@NotBlank(message = "Fyll i det här fältet !!")
	private String descr;

	@ManyToOne
	@JsonIgnore
	private User user;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "krets_id")
	private Krets kret;

	@OneToMany(mappedBy = "news")
	private List<Rating> ratings;

	public int getnId() {
		return nId;
	}

	public void setnId(int nId) {
		this.nId = nId;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	

	public Krets getKret() {
		return kret;
	}

	public void setKret(Krets kret) {
		this.kret = kret;
	}
	
	public Rating getLastRating() {
	    List<Rating> ratings = getRatings(); // Assuming there's a getter for ratings in News
	    if (!ratings.isEmpty()) {
	        return ratings.stream()
	                .max(Comparator.comparing(Rating::getId)) // Assuming higher ID means a more recent rating
	                .orElse(null);
	    }
	    return null;
	}

	@Override
	public String toString() {
		return "News [nId=" + nId + ", rubrik=" + rubrik + ", weblink=" + weblink + ", category=" + category
				+ ", datepicker=" + datepicker + ", krets=" + krets + ", imageUrl=" + imageUrl + ", descr=" + descr
				+ ", user=" + user + "]";
	}

}
