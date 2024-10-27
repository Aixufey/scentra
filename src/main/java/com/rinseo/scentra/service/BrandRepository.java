package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
