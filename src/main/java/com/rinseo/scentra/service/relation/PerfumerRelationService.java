package com.rinseo.scentra.service.relation;

import com.rinseo.scentra.model.Perfumer;

import java.util.List;

public interface PerfumerRelationService {
    List<Perfumer> getPerfumersRelation(long fragranceId);

    List<Perfumer> updatePerfumerRelation(long fragranceId, long perfumerId);

    void deletePerfumerRelation(long fragranceId, long perfumerId);
}
