package com.produkt.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.produkt.model.Rating;
import com.produkt.model.User;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	@Query("from Rating as c where c.user.id =:userId")
	public List<Rating> findRatingByUser(@Param("userId") int userId);

	public List<Rating> findByStarsContainingAndUser(String rubrik, User user);

	public List<Rating> findAll();

	public List<Rating> findByStarsContaining(String stars);

	@Query("SELECT count(r) FROM Rating r WHERE r.stars = 5")
	Integer countStarsWithRatingExcellent();

	@Query("SELECT count(r) FROM Rating r WHERE r.stars = 4")
	Integer countStarsWithRatingGood();

	@Query("SELECT count(r) FROM Rating r WHERE r.stars = 3")
	Integer countStarsWithRatingInteresting();

	@Query("SELECT count(r) FROM Rating r WHERE r.stars = 2")
	Integer countStarsWithRatingAverage();

	@Query("SELECT count(r) FROM Rating r WHERE r.stars = 1")
	Integer countStarsWithRatingBad();

	@Query("SELECT r.stars FROM Rating r WHERE r.news.nId = :nId AND r.user.id = :userId")
	List<Integer> findRatingByNewsIdAndUserId(@Param("nId") Integer nId, @Param("userId") Integer userId);
	
	@Query("SELECT r.stars FROM Rating r WHERE r.news.nId = :nId")
	List<Integer> findRatingByNewsId(@Param("nId") Integer nId);

	@Query("SELECT n.id, r FROM News n LEFT JOIN Rating r ON n.id = r.news.id WHERE (n.id, r.id) IN "
	        + "(SELECT n2.id, MAX(r2.id) FROM News n2 LEFT JOIN Rating r2 ON n2.id = r2.news.id GROUP BY n2.id) "
	        + "ORDER BY n.id ASC")
	List<Object[]> findLatestRatingsAndNewsIds();
}
