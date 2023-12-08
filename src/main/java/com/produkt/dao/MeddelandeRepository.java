package com.produkt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.produkt.model.Meddelande;
import com.produkt.model.User;

@Repository
public interface MeddelandeRepository extends JpaRepository<Meddelande, Integer> {

	@Query("from Meddelande as c where c.user.id =:userId")
	public List<Meddelande> findMeddelandeByUser(@Param("userId") int userId);
	
	public List<Meddelande> findByKrets(String krets);

	public List<Meddelande> findByRubrikContainingAndUser(String rubrik, User user);

	public List<Meddelande> findAll();

	public List<Meddelande> findByRubrikContaining(String rubrik);

}
