package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FragranceRepository extends JpaRepository<Fragrance, Long> {
    List<Fragrance> findFragrancesByNameContainsIgnoreCase(String name);
}
