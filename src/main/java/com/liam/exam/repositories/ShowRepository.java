package com.liam.exam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.liam.exam.models.Show;

@Repository
public interface ShowRepository extends CrudRepository<Show, Long> {
	
	List<Show> findAll();
	
	boolean existsByTitle(String title);

}
