package com.produkt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.produkt.model.Event;
import com.produkt.model.Krets;
import com.produkt.model.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	  @Query("from Event as c where c.user.id =:userId")
	    public List<Event> findEventByUser(@Param("userId") int userId);
		 public List<Event> findByRubrikContainingAndUser(String rubrik,User user); 
		 
		 public List<Event> findAll();
		 public List<Event> findByRubrikContaining(String rubrik);
		 List<Event> findByKret(Krets krets);
}
