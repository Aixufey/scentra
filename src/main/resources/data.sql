-- Insert countries
INSERT INTO country (name)
VALUES ('Afghanistan'),
       ('Albania'),
       ('Algeria'),
       ('Andorra'),
       ('Angola'),
       ('Antigua and Barbuda'),
       ('Argentina'),
       ('Armenia'),
       ('Australia'),
       ('Austria'),
       ('Azerbaijan'),
       ('Bahamas'),
       ('Bahrain'),
       ('Bangladesh'),
       ('Barbados'),
       ('Belarus'),
       ('Belgium'),
       ('Belize'),
       ('Benin'),
       ('Bhutan'),
       ('Bolivia'),
       ('Bosnia and Herzegovina'),
       ('Botswana'),
       ('Brazil'),
       ('Brunei'),
       ('Bulgaria'),
       ('Burkina Faso'),
       ('Burundi'),
       ('Cabo Verde'),
       ('Cambodia'),
       ('Cameroon'),
       ('Canada'),
       ('Central African Republic'),
       ('Chad'),
       ('Chile'),
       ('China'),
       ('Colombia'),
       ('Comoros'),
       ('Costa Rica'),
       ('Croatia'),
       ('Cuba'),
       ('Cyprus'),
       ('Czech Republic'),
       ('Democratic Republic of the Congo'),
       ('Denmark'),
       ('Djibouti'),
       ('Dominica'),
       ('Dominican Republic'),
       ('East Timor (Timor-Leste)'),
       ('Ecuador'),
       ('Egypt'),
       ('El Salvador'),
       ('Equatorial Guinea'),
       ('Eritrea'),
       ('Estonia'),
       ('Ethiopia'),
       ('Fiji'),
       ('Finland'),
       ('France'),
       ('Gabon'),
       ('Gambia'),
       ('Georgia'),
       ('Germany'),
       ('Ghana'),
       ('Greece'),
       ('Grenada'),
       ('Guatemala'),
       ('Guinea'),
       ('Guinea-Bissau'),
       ('Guyana'),
       ('Haiti'),
       ('Honduras'),
       ('Hungary'),
       ('Iceland'),
       ('India'),
       ('Indonesia'),
       ('Iran'),
       ('Iraq'),
       ('Ireland'),
       ('Israel'),
       ('Italy'),
       ('Jamaica'),
       ('Japan'),
       ('Jordan'),
       ('Kazakhstan'),
       ('Kenya'),
       ('Kiribati'),
       ('Kuwait'),
       ('Kyrgyzstan'),
       ('Laos'),
       ('Latvia'),
       ('Lebanon'),
       ('Lesotho'),
       ('Liberia'),
       ('Libya'),
       ('Liechtenstein'),
       ('Lithuania'),
       ('Luxembourg'),
       ('Madagascar'),
       ('Malawi'),
       ('Malaysia'),
       ('Maldives'),
       ('Mali'),
       ('Malta'),
       ('Marshall Islands'),
       ('Mauritania'),
       ('Mauritius'),
       ('Mexico'),
       ('Micronesia'),
       ('Moldova'),
       ('Monaco'),
       ('Mongolia'),
       ('Montenegro'),
       ('Morocco'),
       ('Mozambique'),
       ('Myanmar'),
       ('Namibia'),
       ('Nauru'),
       ('Nepal'),
       ('Netherlands'),
       ('New Zealand'),
       ('Nicaragua'),
       ('Niger'),
       ('Nigeria'),
       ('North Korea'),
       ('North Macedonia'),
       ('Norway'),
       ('Oman'),
       ('Pakistan'),
       ('Palau'),
       ('Panama'),
       ('Papua New Guinea'),
       ('Paraguay'),
       ('Peru'),
       ('Philippines'),
       ('Poland'),
       ('Portugal'),
       ('Qatar'),
       ('Romania'),
       ('Russia'),
       ('Rwanda'),
       ('Saint Kitts and Nevis'),
       ('Saint Lucia'),
       ('Saint Vincent and the Grenadines'),
       ('Samoa'),
       ('San Marino'),
       ('Sao Tome and Principe'),
       ('Saudi Arabia'),
       ('Senegal'),
       ('Serbia'),
       ('Seychelles'),
       ('Sierra Leone'),
       ('Singapore'),
       ('Slovakia'),
       ('Slovenia'),
       ('Solomon Islands'),
       ('Somalia'),
       ('South Africa'),
       ('South Korea'),
       ('South Sudan'),
       ('Spain'),
       ('Sri Lanka'),
       ('Sudan'),
       ('Suriname'),
       ('Sweden'),
       ('Switzerland'),
       ('Syria'),
       ('Taiwan'),
       ('Tajikistan'),
       ('Tanzania'),
       ('Thailand'),
       ('Togo'),
       ('Tonga'),
       ('Trinidad and Tobago'),
       ('Tunisia'),
       ('Turkey'),
       ('Turkmenistan'),
       ('Tuvalu'),
       ('Uganda'),
       ('Ukraine'),
       ('United Arab Emirates'),
       ('United Kingdom'),
       ('United States of America'),
       ('Uruguay'),
       ('Uzbekistan'),
       ('Vanuatu'),
       ('Vatican City'),
       ('Venezuela'),
       ('Vietnam'),
       ('Yemen'),
       ('Zambia'),
       ('Zimbabwe');

-- Insert accords
INSERT INTO accord (name, description)
VALUES
    ('citrus', 'Most often by citrus in perfumery we describe the whole spectrum of hesperidic fruits (Hesperidia), named after the Hesperides, nymphs from Greek mythology.'),
    ('fruits', 'Fruits and vegetables provide a nuanced texture and a refreshing feel in fragrances.'),
    ('flowers', 'Floral scents add a romantic and often feminine touch to a composition, augmenting the feel of natural beauty derived from smelling a composition.'),
    ('greens', 'By the term "green" we refer to notes of snapped leaves and freshly-cut grasses, which exude a piquant quality.'),
    ('spices', 'The Spices group is a familiar category of perfume notes, thanks mainly to their long-standing inclusion in food.'),
    ('sweets', 'This succulent group of scent notes has really established itself and multiplied henceforth with the advent of "gourmand" fragrances, a sub-division of the Oriental fragrance group, in the 1990s and 2000s.'),
    ('woods', 'Woody notes are dependable and pliable, a sort of a Jack in the deck of a skilled perfumer, providing the bottom of a composition and reinforcing the other elements according to their olfactory profile.'),
    ('resins', 'The raw materials falling under the umbrella of resins and balsams are among the most ancient components of perfumes, often the basis of the Oriental family of scents.'),
    ('musks', 'In perfumery, animal notes were traditionally rendered through deer musk, castoreum, ambergris and civet cats, but nowadays, ethical concerns for these animals welfare have rendered their use obsolete and the substitution with synthetic variants a rule.'),
    ('beverages', 'Beverage notes in fragrances provide a succulent, appetizing effect, often combined in fruity floral blends or "gourmand" fragrances which seduce the taste buds as well as the nostrils.'),
    ('natural', 'In this group we place descriptive notes such as powdery, earthy, and some unusual smells which could be found in perfume compositions.');

-- Insert notes
INSERT INTO note (name, description, accord_id)
VALUES
    ('bergamot', 'Citrusy, bitter & tart, elegant, light note with mild spicy tone, complex with nuances of fruit and aromatic elements, reminiscent of eau de Cologne, it flavors Earl Grey tea.', 1),
    ('sea notes', 'A fresh salty fragrance of the sea.', 11),
    ('rosemary', 'Аn aromatic culinary herb with a camphorous, minty profile.', 4),
    ('sage', 'Savory, aromatic culinary note with hazy, soft and mildly peppery aspects belonging to the salvia family.', 4),
    ('geranium', 'Leaves are distilled to give a full rosy nuance with green aromatic undertones.', 3),
    ('incense', 'Olibanum or Frankincense is usually meant by incense in a fragrance pyramid. It could also refer to a smoky note.', 8),
    ('patchouli', 'An exotic bush that grows mainly in India, the leaves of which produce the essential oil of patchouli. Sweet, dark, with an earthy, woody edge.', 7),
    ('leather', 'Synthetic or naturally derived note of pungent characteristics, reminiscent of cured hides and leather goods. Usually rendered by birch tar or by synth isoquinolines', 9),
    ('cypress', 'A dry woody earthy aromatic and very tenacious odor.', 7),
    ('vetiver', 'Vetivers from different parts of the world differ a lot. For example, Haitian vetiver is clean and ethereal, while the Javanese one is smoky and dusty. If we have to generalize the scent, it is earthy, woody, green.', 7),
    ('woody notes', 'Umbrella term used to refer to fragrance notes coming from woody materials (trees mostly, as well as some bushes -such as patchouli- or a few grasses -such as vetiver)', 7),
    ('saffron', 'Its odor profile is bittersweet, leathery, soft and intimate, with an earthy base note.', 5),
    ('jasmine', 'Sweet, white floral, opulent, can be more indolic or greener and fresher when synthesized in the lab.', 3),
    ('amberwood', 'Amberwood is a synthetic note that exudes a warm, rich, woody aroma with a slight hint of ambergris. Its scent profile combines the earthy depth of wood with the sweet, resinous undertones of amber.', 8),
    ('ambergris', 'A product of the intestines of sperm whales found floating on the ocean and blanched by sea and sun with a skin-like salty and warm effect. Synthetically recreated today.', 9),
    ('hedione', 'A fresh floral fragrance reminiscent of jasmine with green nuances.', 11),
    ('fir', 'Fir absolute is a sweet balsamic, aromatic green spicy fragrance.', 7),
    ('cedar', 'An soft woody note coming from either the Atlas Mountains (Morocco) or the Virginia (US) cedarwood. There are also many cedar-smelling synthetics in use.', 7),
    ('sugar', 'A very popular sugary sweet accord can be also fruity, chocolate or compliment any other note.', 6),
    ('ambroxan', 'A modern synthetic compound with a dry amber musky smell.', 9),
    ('oakmoss', 'An inky, bitter-smelling forest floor evocative. It is an essential part of chypre fragrances and fougère fragrances', 7);

-- Insert concentrations of fragrances
INSERT INTO concentration (name, description)
VALUES
    ('EDC', 'eau de cologne'),
    ('EDT', 'eau de toilette'),
    ('EDP', 'eau de perfum'),
    ('Parfum', 'extrait de parfum');

-- Insert companies
INSERT INTO company (name, country_id)
SELECT 'LVMH', id FROM country WHERE name = 'France';
INSERT INTO company (name, country_id)
SELECT 'Estée Lauder Companies', id FROM country WHERE name = 'United States of America';
INSERT INTO company (name, country_id)
SELECT 'Coty Inc.', id FROM country WHERE name = 'United States of America';
INSERT INTO company (name, country_id)
SELECT 'Procter & Gamble', id FROM country WHERE name = 'United States of America';
INSERT INTO company (name, country_id)
SELECT 'Shiseido', id FROM country WHERE name = 'Japan';
INSERT INTO company (name, country_id)
SELECT 'L’Oréal', id FROM country WHERE name = 'France';
INSERT INTO company (name, country_id)
SELECT 'Chanel', id FROM country WHERE name = 'France';
INSERT INTO company (name, country_id)
SELECT 'Puig', id FROM country WHERE name = 'Spain';
INSERT INTO company (name, country_id)
SELECT 'Firmenich', id FROM country WHERE name = 'Switzerland';

-- Insert brands
INSERT INTO brand (name, country_id, company_id)
SELECT 'Giorgio Armani',
       (SELECT id FROM country WHERE name = 'Italy'),
       (SELECT id FROM company WHERE name = 'L’Oréal');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Christian Dior',
       (SELECT id FROM country WHERE name = 'France'),
       (SELECT id FROM company WHERE name = 'LVMH');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Chanel',
       (SELECT id FROM country WHERE name = 'France'),
       (SELECT id FROM company WHERE name = 'Chanel');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Tom Ford',
       (SELECT id FROM country WHERE name = 'United States of America'),
       (SELECT id FROM company WHERE name = 'Estée Lauder Companies');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Calvin Klein',
       (SELECT id FROM country WHERE name = 'United States of America'),
       (SELECT id FROM company WHERE name = 'Coty Inc.');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Gucci',
       (SELECT id FROM country WHERE name = 'Italy'),
       (SELECT id FROM company WHERE name = 'Coty Inc.');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Guerlain',
       (SELECT id FROM country WHERE name = 'France'),
       (SELECT id FROM company WHERE name = 'LVMH');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Yves Saint Laurent',
       (SELECT id FROM country WHERE name = 'France'),
       (SELECT id FROM company WHERE name = 'L’Oréal');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Paco Rabanne',
       (SELECT id FROM country WHERE name = 'Spain'),
       (SELECT id FROM company WHERE name = 'Puig');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Shiseido',
       (SELECT id FROM country WHERE name = 'Japan'),
       (SELECT id FROM company WHERE name = 'Shiseido');
INSERT INTO brand (name, country_id, company_id)
SELECT 'Maison Francis Kurkdjian',
       (SELECT id FROM country WHERE name = 'France'),
       (SELECT id FROM company WHERE name = 'LVMH');

-- Insert perfumers
INSERT INTO perfumer (name, company_id, country_id)
SELECT 'Alberto Morillas',
       (SELECT id FROM company WHERE name = 'Firmenich'),
       (SELECT id FROM country WHERE name = 'Spain');
INSERT INTO perfumer (name, company_id, country_id)
SELECT 'Jacques Polge',
       (SELECT id FROM company WHERE name = 'Chanel'),
       (SELECT id FROM country WHERE name = 'France');
INSERT INTO perfumer (name, company_id, country_id)
SELECT 'Francis Kurkdjian',
       (SELECT id FROM company WHERE name = 'LVMH'),
       (SELECT id FROM country WHERE name = 'France');
INSERT INTO perfumer (name, company_id, country_id)
SELECT 'Olivier Cresp',
       (SELECT id FROM company WHERE name = 'Puig'),
       (SELECT id FROM country WHERE name = 'France');
INSERT INTO perfumer (name, company_id, country_id)
SELECT 'Thierry Wasser',
       (SELECT id FROM company WHERE name = 'LVMH'),
       (SELECT id FROM country WHERE name = 'France');

-- Insert JUNCTION TABLE relationships between perfumers and brands
INSERT INTO perfumer_brand (perfumer_id, brand_id)
SELECT (SELECT id FROM perfumer WHERE name = 'Alberto Morillas'),
       (SELECT id FROM brand WHERE name = 'Giorgio Armani');

INSERT INTO perfumer_brand (perfumer_id, brand_id)
SELECT (SELECT id FROM perfumer WHERE name = 'Alberto Morillas'),
       (SELECT id FROM brand WHERE name = 'Gucci');

INSERT INTO perfumer_brand (perfumer_id, brand_id)
SELECT (SELECT id FROM perfumer WHERE name = 'Jacques Polge'),
       (SELECT id FROM brand WHERE name = 'Chanel');

INSERT INTO perfumer_brand (perfumer_id, brand_id)
SELECT (SELECT id FROM perfumer WHERE name = 'Francis Kurkdjian'),
       (SELECT id FROM brand WHERE name = 'Maison Francis Kurkdjian');

INSERT INTO perfumer_brand (perfumer_id, brand_id)
SELECT (SELECT id FROM perfumer WHERE name = 'Olivier Cresp'),
       (SELECT id FROM brand WHERE name = 'Paco Rabanne');

INSERT INTO perfumer_brand (perfumer_id, brand_id)
SELECT (SELECT id FROM perfumer WHERE name = 'Thierry Wasser'),
       (SELECT id FROM brand WHERE name = 'Guerlain');


-- Insert fragrances
INSERT INTO fragrance (name, "year", brand_id, country_id)
SELECT 'Acqua di Giò Profumo', 2015,
       (SELECT id FROM brand WHERE name = 'Giorgio Armani'),
       (SELECT id FROM country WHERE name = 'Italy');
INSERT INTO fragrance (name, "year", brand_id, country_id)
SELECT 'Gucci Guilty Absolute', 2017,
       (SELECT id FROM brand WHERE name = 'Gucci'),
       (SELECT id FROM country WHERE name = 'Italy');
INSERT INTO fragrance (name, "year", brand_id, country_id)
SELECT 'Baccarat Rouge 540', 2015,
       (SELECT id FROM brand WHERE name = 'Maison Francis Kurkdjian'),
       (SELECT id FROM country WHERE name = 'France');

-- Insert JUNCTION TABLE relationships between fragrances and concentrations
INSERT INTO fragrance_concentration (fragrance_id, concentration_id)
SELECT (SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
       (SELECT id FROM concentration WHERE name = 'EDP');
INSERT INTO fragrance_concentration (fragrance_id, concentration_id)
SELECT (SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
       (SELECT id FROM concentration WHERE name = 'EDP');
INSERT INTO fragrance_concentration (fragrance_id, concentration_id)
SELECT (SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
       (SELECT id FROM concentration WHERE name = 'Parfum');

-- Insert JUNCTION TABLE relationships between fragrances and perfumers
INSERT INTO fragrance_perfumer (fragrance_id, perfumer_id)
SELECT (SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
       (SELECT id FROM perfumer WHERE name = 'Alberto Morillas');
INSERT INTO fragrance_perfumer (fragrance_id, perfumer_id)
SELECT (SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
       (SELECT id FROM perfumer WHERE name = 'Alberto Morillas');
INSERT INTO fragrance_perfumer (fragrance_id, perfumer_id)
SELECT (SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
       (SELECT id FROM perfumer WHERE name = 'Francis Kurkdjian');

-- Insert JUNCTION TABLE relationships between fragrances and notes
INSERT INTO fragrance_notes (fragrance_id, note_id)
VALUES
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'bergamot')),
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'sea notes')),
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'rosemary')),
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'sage')),
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'geranium')),
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'incense')),
    ((SELECT id FROM fragrance WHERE name = 'Acqua di Giò Profumo'),
     (SELECT id FROM note WHERE name = 'patchouli'));

INSERT INTO fragrance_notes (fragrance_id, note_id)
VALUES
    ((SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
     (SELECT id FROM note WHERE name = 'leather')),
    ((SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
     (SELECT id FROM note WHERE name = 'cypress')),
    ((SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
     (SELECT id FROM note WHERE name = 'vetiver')),
    ((SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
     (SELECT id FROM note WHERE name = 'patchouli')),
    ((SELECT id FROM fragrance WHERE name = 'Gucci Guilty Absolute'),
     (SELECT id FROM note WHERE name = 'woody notes'));

INSERT INTO fragrance_notes (fragrance_id, note_id)
VALUES
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'saffron')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'jasmine')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'amberwood')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'ambergris')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'hedione')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'fir')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'cedar')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'sugar')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'ambroxan')),
    ((SELECT id FROM fragrance WHERE name = 'Baccarat Rouge 540'),
     (SELECT id FROM note WHERE name = 'oakmoss'));
