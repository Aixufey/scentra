package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    List<Company> getAll();

    Company getById(long id);

    CompanyDTO create(CompanyDTO company);

    CompanyDTO update(long id, CompanyDTO company);

    void delete(long id);
}
