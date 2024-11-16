package com.rinseo.scentra.service;

import com.rinseo.scentra.exception.FragranceNotFoundException;
import com.rinseo.scentra.model.Fragrance;

import java.util.ArrayList;
import java.util.List;

//@Service
public class FragranceService {
    private static final List<Fragrance> fragrances = new ArrayList<>();
    private static long fragranceCount = 0;

    static {
        fragrances.add(new Fragrance(++fragranceCount, "Acqua Di Gio", 2015, ""));
        fragrances.add(new Fragrance(++fragranceCount, "Dior Sauvage", 2016, ""));
        fragrances.add(new Fragrance(++fragranceCount, "Bleu De Chanel", 2014, ""));
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
