package com.produkt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produkt.model.Krets;


public interface KretsRepository extends JpaRepository<Krets, Integer> {
	
	   Krets findByKretName(String kretsName);

	List<Krets> findByKretNameIn(List<String> selectedKrets);
	   
	   

	                 }