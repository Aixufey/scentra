package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.model.Perfumer;

import java.util.List;

public interface FragrancePerfumerService {
    List<Perfumer> getAll(long fragranceId);

    List<Perfumer> updatePerfumers(long fragranceId, List<Long> perfumerIds);

    void deletePerfumer(long fragranceId, long perfumerId);
}
