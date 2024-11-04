package com.rinseo.scentra.service.relation;

import com.rinseo.scentra.model.Concentration;

import java.util.List;

public interface ConcentrationRelationService {
    List<Concentration> getConcentrationsRelation(long fragranceId);

    List<Concentration> updateConcentrationRelation(long fragranceId, long concentrationId);

    void deleteConcentrationRelation(long fragranceId, long concentrationId);
}
