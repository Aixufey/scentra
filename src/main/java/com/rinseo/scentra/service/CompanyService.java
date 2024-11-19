package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.dto.CompanyDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    List<Company> getAll();

    Company getById(long id);

    CompanyDTO create(CompanyDTO company, MultipartFile file);

    CompanyDTO update(long id, CompanyDTO company, MultipartFile file);

    void delete(long id);
}
