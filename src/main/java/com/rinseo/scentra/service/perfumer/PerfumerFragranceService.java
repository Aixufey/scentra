package com.rinseo.scentra.service.perfumer;

import com.rinseo.scentra.model.Fragrance;

import java.util.List;

public interface PerfumerFragranceService {
    List<Fragrance> getAll(long perfumerId);

    List<Fragrance> updateFragrances(long perfumerId, List<Long> fragranceIds);

    void deleteFragrance(long perfumerId, long fragranceId);

    void deleteAll(long perfumerId);
}
