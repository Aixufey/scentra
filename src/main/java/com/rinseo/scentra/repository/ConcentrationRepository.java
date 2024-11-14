package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Concentration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {
}
