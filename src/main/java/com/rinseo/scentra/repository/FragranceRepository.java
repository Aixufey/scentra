package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FragranceRepository extends JpaRepository<Fragrance, Long> {
    List<Fragrance> findFragrancesByNameContainsIgnoreCase(String name);

    // Default JPQL
    @Query("SELECT f FROM Fragrance f WHERE f.name LIKE 'A%'")
    List<Fragrance> findFragrancesStartsWithA();

    // Native SQL
    @Query(value = "SELECT * FROM Fragrance WHERE name = :name", nativeQuery = true)
    Fragrance findByName(@Param("name") String name);
}
