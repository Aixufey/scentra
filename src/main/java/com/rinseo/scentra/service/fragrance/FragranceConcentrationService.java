package com.rinseo.scentra.service.fragrance;

import com.rinseo.scentra.model.Concentration;

import java.util.List;

public interface FragranceConcentrationService {
    List<Concentration> getAll(long fragranceId);

    Concentration updateConcentration(long fragranceId, long concentrationId);

    void deleteConcentration(long fragranceId, long concentrationId);

    void deleteAll(long fragranceId);
}
