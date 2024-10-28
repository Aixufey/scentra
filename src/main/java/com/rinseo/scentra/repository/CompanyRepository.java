package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
