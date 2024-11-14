package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Accord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccordRepository extends JpaRepository<Accord, Long> {

}
