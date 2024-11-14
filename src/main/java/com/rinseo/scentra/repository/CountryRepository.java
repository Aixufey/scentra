package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findCountryByNameEqualsIgnoreCase(String name);
}
