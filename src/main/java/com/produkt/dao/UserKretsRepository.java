package com.produkt.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produkt.model.UserKrets;

public interface UserKretsRepository extends JpaRepository<UserKrets, Integer> {
	List<UserKrets> findKretsByUserId(int userId);
}