package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FragranceRepository extends JpaRepository<Fragrance, Long> {
    Fragrance findByNameEqualsIgnoreCase(String name);
}
