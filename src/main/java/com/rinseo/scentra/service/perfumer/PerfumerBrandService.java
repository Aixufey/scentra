package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.model.Brand;

import java.util.List;

public interface PerfumerBrandService {
    List<Brand> getAll(long perfumerId);

    List<Brand> updateBrand(long perfumerId, long brandId);

    void deleteBrand(long perfumerId, long brandId);

    void deleteAll(long perfumerId);
}
