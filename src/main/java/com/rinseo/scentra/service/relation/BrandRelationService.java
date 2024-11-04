package com.rinseo.scentra.service.relation;

import com.rinseo.scentra.model.Fragrance;

public interface BrandRelationService {
    Fragrance updateBrandRelation(long fragranceId, long brandId);

    void deleteBrandRelation(long fragranceId, long brandId);
}
