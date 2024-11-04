package com.rinseo.scentra.service.relation;

import com.rinseo.scentra.model.Fragrance;

public interface CountryRelationService {
    Fragrance updateCountryRelation(long fragranceId, long countryId);

    void deleteCountryRelation(long fragranceId, long countryId);
}
