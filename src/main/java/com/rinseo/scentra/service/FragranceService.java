package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Fragrance;
import com.rinseo.scentra.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FragranceService {
    private static final List<Fragrance> fragrances = new ArrayList<>();
    private static long fragranceCount = 0;
    static {
        fragrances.add(new Fragrance(++fragranceCount, "Acqua Di Gio", 2015, new ArrayList<>(List.of(
                new Note(1L, "Bergamot", ""),
                new Note(2L, "Sea Notes", ""),
                new Note(3L, "Rosemary", ""),
                new Note(4L, "Sage", ""),
                new Note(5L, "Geranium", ""),
                new Note(6L, "Incense", ""),
                new Note(7L, "Patchouli", "")
        ))));
        fragrances.add(new Fragrance(++fragranceCount, "Dior Sauvage", 2016, new ArrayList<>(List.of(
                new Note(1L, "Bergamot", ""),
                new Note(8L, "Sichuan Pepper", ""),
                new Note(9L, "Lavender", ""),
                new Note(10L, "Star Anise", ""),
                new Note(11L, "Nutmeg", ""),
                new Note(12L, "Ambroxan", ""),
                new Note(13L, "Vanilla", "")
        ))));
        fragrances.add(new Fragrance(++fragranceCount, "Bleu De Chanel", 2014, new ArrayList<>(List.of(
                new Note(14L, "Grapefruit", ""),
                new Note(15L, "Lemon", ""),
                new Note(16L, "Mint", ""),
                new Note(17L, "Pink Pepper", ""),
                new Note(18L, "Ginger", ""),
                new Note(19L, "Jasmine", ""),
                new Note(20L, "Nutmeg", ""),
                new Note(21L, "Iso E Super", ""),
                new Note(6L, "Incense", ""),
                new Note(22L, "Vetiver", ""),
                new Note(23L, "Cedar", ""),
                new Note(24L, "Sandalwood", ""),
                new Note(25L, "Labdanum", ""),
                new Note(26L, "White Musk", ""),
                new Note(7L, "Patchouli", "")
        ))));
    }

    public List<Fragrance> getAll() {
        return fragrances;
    }

    public Fragrance getById(long id) {
        return fragrances.stream()
                .filter(frag -> frag.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with id " + id + " not found"));
    }

    public Fragrance getByName(String name) {
        return fragrances.stream()
                .filter(frag -> frag.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new FragranceNotFoundException("Fragrance with name " + name + " not found"));
    }

    public boolean deleteById(long id) {
        boolean removed = fragrances.removeIf(frag -> frag.getId() == id);
        if (!removed) {
            throw new FragranceNotFoundException("Fragrance with id " + id + " not found");
        }
        return removed;
    }

    public void updateById(long id, Fragrance fragrance) {
        Fragrance updatedFragrance = getById(id);
        updatedFragrance.setName(fragrance.getName());
        updatedFragrance.setYear(fragrance.getYear());
    }

    public Fragrance save(Fragrance fragrance) {
        fragrance.setId(++fragranceCount);
        fragrances.add(fragrance);
        return fragrance;
    }

}
