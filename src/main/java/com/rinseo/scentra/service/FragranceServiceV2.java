package com.rinseo.scentra.service;

import com.rinseo.scentra.model.Concentration;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Note;
import com.rinseo.scentra.model.Perfumer;
import com.rinseo.scentra.model.dto.FragranceDTO;

import java.util.List;

public interface FragranceServiceV2 {
    List<Fragrance> getAll();

    Fragrance getById(long id);

    List<Fragrance> getByName(String name);

    FragranceDTO create(FragranceDTO fragrance);

    FragranceDTO update(long id, FragranceDTO fragrance);

    void deleteById(long id);

    Fragrance updateBrandRelation(long fragranceId, long brandId);

    void deleteBrandRelation(long fragranceId, long brandId);

    Fragrance updateCountryRelation(long fragranceId, long countryId);

    void deleteCountryRelation(long fragranceId, long countryId);

    List<Perfumer> getPerfumersRelation(long fragranceId);
    List<Perfumer> updatePerfumerRelation(long fragranceId, long perfumerId);
    void deletePerfumerRelation(long fragranceId, long perfumerId);

    List<Concentration> getConcentrationsRelation(long fragranceId);
    List<Concentration> updateConcentrationRelation(long fragranceId, long concentrationId);
    void deleteConcentrationRelation(long fragranceId, long concentrationId);

    List<Note> getNotesRelation(long fragranceId);
    List<Note> updateNoteRelation(long fragranceId, long noteId);
    void deleteNoteRelation(long fragranceId, long noteId);
}
