package com.produkt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.produkt.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	

    @Query("Select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);
    
    List<User> findByKrets(String krets);
    
    List<User> findByKretsAndRole(String krets, String role);

}
