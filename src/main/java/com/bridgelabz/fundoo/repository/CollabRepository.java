package com.bridgelabz.fundoo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoo.model.CollaBorator;

@Repository
public interface CollabRepository extends JpaRepository<CollaBorator, Integer> {
	Optional<CollaBorator> findByCollabEmail(String emailString);


	
	
	
	


}
