package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.exception.CompanyNotFoundException;
import com.rinseo.scentra.exception.PerfumerNotFoundException;
import com.rinseo.scentra.model.Company;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.repository.CompanyRepository;
import com.rinseo.scentra.repository.PerfumerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PerfumerCompanyServiceImpl implements PerfumerCompanyService {
    private final PerfumerRepository repo;
    private final CompanyRepository companyRepository;

    @Override
    public Company getCompany(long perfumerId) {
        Perfumer perfumer = repo.findById(perfumerId)
                .orElseThrow(() -> new IllegalStateException("Perfumer with id " + perfumerId + " not found"));

        if (perfumer.getCompany() == null) {
            throw new CompanyNotFoundException("Perfumer with id " + perfumerId + " does not have a company");
        }

        return perfumer.getCompany();
    }

    @Override
    @Transactional
    public Company updateCompany(long perfumerId, long companyId) {
        Perfumer perfumer = repo.findById(perfumerId)
                .orElseThrow(() -> new IllegalStateException("Perfumer with id " + perfumerId + " not found"));

        Company company = getPerfumerCompany(companyId);
        perfumer.setCompany(company);

        repo.saveAndFlush(perfumer);
        return company;
    }

    @Override
    @Transactional
    public void deleteCompany(long perfumerId) {
        Perfumer perfumer = repo.findById(perfumerId)
                .orElseThrow(() -> new PerfumerNotFoundException("Perfumer with id " + perfumerId + " not found"));

        perfumer.setCompany(null);

        repo.saveAndFlush(perfumer);
    }

    // Helper method to get company by id
    public Company getPerfumerCompany(long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id " + companyId + " not found"));
    }
}
