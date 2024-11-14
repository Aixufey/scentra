package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.model.Country;

public interface FragranceCountryService {
    Country getCountry(long fragranceId);

    Country updateCountry(long fragranceId, long countryId);

    void deleteCountry(long fragranceId);
}
