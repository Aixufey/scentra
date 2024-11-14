package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.model.Brand;

public interface FragranceBrandService {
    Brand getBrand(long fragranceId);

    Brand updateBrand(long fragranceId, long brandId);

    void deleteBrand(long fragranceId);
}
