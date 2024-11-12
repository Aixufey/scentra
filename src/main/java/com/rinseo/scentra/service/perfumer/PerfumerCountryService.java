package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.model.Country;

public interface PerfumerCountryService {
    Country getCountry(long perfumerId);

    Country updateCountry(long perfumerId, long countryId);

    void deleteCountry(long perfumerId);
}
