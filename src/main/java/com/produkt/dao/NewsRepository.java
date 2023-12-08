package com.produkt.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.produkt.model.Krets;
import com.produkt.model.News;
import com.produkt.model.User;
import com.produkt.model.UserKrets;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

	  @Query("from News as c where c.user.id =:userId")
	    public Page<News> findNewsByUser(@Param("userId") int userId, Pageable p);
		 public List<News> findByRubrikContainingAndUser(String rubrik,User user); 
		 
		 public List<News> findAll();
		 public List<News> findByRubrikContaining(String rubrik);
		 
		 List<News> findByKret(Krets krets);
		 List<String> findDatepickerByKretOrderByDatepickerDesc(UserKrets userKrets);

		    List<News> findByKretAndDatepickerInOrderByDatepickerDesc(Krets kret, List<String> datepickerList);
		
}
