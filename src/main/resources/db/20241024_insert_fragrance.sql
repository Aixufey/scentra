-- Insert Acqua di Giò Profondo
INSERT INTO fragrance (name, "year", brand_id, country_id)
SELECT 'Acqua di Giò Profondo', 2020,
       (SELECT brand_id FROM brand WHERE name = 'Giorgio Armani'),
       (SELECT country_id FROM country WHERE name = 'Italy');
INSERT INTO fragrance_concentration (fragrance_id, concentration_id)
SELECT (SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
       (SELECT concentration_id FROM concentration WHERE name = 'EDP');
INSERT INTO fragrance_perfumer (fragrance_id, perfumer_id)
SELECT (SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
       (SELECT perfumer_id FROM perfumer WHERE name = 'Alberto Morillas');
INSERT INTO fragrance_notes (fragrance_id, note_id)
VALUES
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'sea notes')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'bergamot')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'mandarin orange')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'rosemary')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'lavender')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'cypress')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'mastic')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'mineral notes')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'musk')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'patchouli')),
    ((SELECT fragrance_id FROM fragrance WHERE name = 'Acqua di Giò Profondo'),
     (SELECT note_id FROM note WHERE name = 'amber'));
