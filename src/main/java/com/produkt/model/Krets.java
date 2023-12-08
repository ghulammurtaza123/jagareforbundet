package com.produkt.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Krets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String kretName;

	// ... other fields ...

	@OneToMany(mappedBy = "krets", cascade = CascadeType.ALL)
	private List<UserKrets> userKrets;
	
	

	@Transient
	private List<String> selectedKrets;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKretName() {
		return kretName;
	}

	public void setKretName(String kretName) {
		this.kretName = kretName;
	}

	public List<UserKrets> getUserKrets() {
		return userKrets;
	}

	public void setUserKrets(List<UserKrets> userKrets) {
		this.userKrets = userKrets;
	}

	public List<String> getSelectedKrets() {
		return selectedKrets;
	}

	public void setSelectedKrets(List<String> selectedKrets) {
		this.selectedKrets = selectedKrets;
	}



	


}
