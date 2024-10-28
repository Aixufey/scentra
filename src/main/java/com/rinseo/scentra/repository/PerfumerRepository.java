package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Perfumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfumerRepository extends JpaRepository<Perfumer, Long> {
    Perfumer findByNameEqualsIgnoreCase(String name);

    List<Perfumer> findByNameContainingIgnoreCase(String name);
}
