package com.liam.exam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liam.exam.models.Show;
import com.liam.exam.repositories.ShowRepository;

@Service
public class ShowService {
	
		@Autowired
		private ShowRepository showRepo;
		
		// Get All
		public List<Show> getAll() {
			return showRepo.findAll();
		}
		
		// Get One
		public Show getOne(Long id) {
			return showRepo.findById(id).orElse(null);
		}
		
		// Create
		public Show createOne(Show show) {
			return showRepo.save(show);
		}
		
		
		// Update
		public Show updateOne(Show show) {
			return showRepo.save(show);
		}
		
		// Delete
		public void deleteOne(Long id) {
			showRepo.deleteById(id);
		}
		
		
		
		public boolean checkTitle(String title) {
			return showRepo.existsByTitle(title);
		}

}
