package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Perfumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumerRepository extends JpaRepository<Perfumer, Long> {
    Perfumer findByNameEqualsIgnoreCase(String name);

    List<Perfumer> findByNameContainingIgnoreCase(String name);
}
