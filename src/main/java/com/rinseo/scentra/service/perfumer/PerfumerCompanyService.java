package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.model.Company;

public interface PerfumerCompanyService {
    Company getCompany(long perfumerId);

    Company updateCompany(long perfumerId, long companyId);

    void deleteCompany(long perfumerId);
}
