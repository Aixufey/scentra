package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Concentration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {
}
