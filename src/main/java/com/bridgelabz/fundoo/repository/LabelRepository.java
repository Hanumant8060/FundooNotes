package com.bridgelabz.fundoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
	Optional<Label> findByLabelId(long labelId);
	Optional<Label> findByLabelname(String labelName);

}
