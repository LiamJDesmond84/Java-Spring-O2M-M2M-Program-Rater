package com.liam.exam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.liam.exam.models.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
	
	List<Rating> findAll();
	
	List<Rating> findAllByOrderByScoreDesc();

}
