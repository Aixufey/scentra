DROP TABLE IF EXISTS country CASCADE;
DROP TABLE IF EXISTS company CASCADE;
DROP TABLE IF EXISTS brand CASCADE;
DROP TABLE IF EXISTS accord CASCADE;
DROP TABLE IF EXISTS note CASCADE;
DROP TABLE IF EXISTS concentration CASCADE;
DROP TABLE IF EXISTS perfumer CASCADE;
DROP TABLE IF EXISTS fragrance CASCADE;
DROP TABLE IF EXISTS fragrance_concentration CASCADE;
DROP TABLE IF EXISTS fragrance_notes CASCADE;
DROP TABLE IF EXISTS fragrance_perfumer CASCADE;
DROP TABLE IF EXISTS perfumer_brand CASCADE;

CREATE TABLE country
(
    country_id SERIAL PRIMARY KEY,
    name       TEXT NOT NULL UNIQUE
);

CREATE TABLE company
(
    company_id SERIAL PRIMARY KEY,
    name       TEXT NOT NULL UNIQUE,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES country (country_id)
);

CREATE TABLE brand
(
    brand_id   SERIAL PRIMARY KEY,
    name       TEXT NOT NULL UNIQUE,
    country_id INT,
    company_id INT,
    FOREIGN KEY (country_id) REFERENCES country (country_id),
    FOREIGN KEY (company_id) REFERENCES company (company_id) ON DELETE RESTRICT
);

CREATE TABLE accord
(
    accord_id   SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE note
(
    note_id     SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT,
    accord_id   INT,
    FOREIGN KEY (accord_id) REFERENCES accord (accord_id)
);

CREATE TABLE concentration
(
    concentration_id SERIAL PRIMARY KEY,
    name             TEXT NOT NULL UNIQUE,
    description      TEXT
);

CREATE TABLE perfumer
(
    perfumer_id SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    company_id  INT,
    country_id  INT,
    FOREIGN KEY (company_id) REFERENCES company (company_id),
    FOREIGN KEY (country_id) REFERENCES country (country_id)
);

CREATE TABLE fragrance
(
    fragrance_id SERIAL PRIMARY KEY,
    name         TEXT NOT NULL UNIQUE,
    year         INT,
    brand_id     INT,
    country_id   INT,
    FOREIGN KEY (brand_id) REFERENCES brand (brand_id),
    FOREIGN KEY (country_id) REFERENCES country (country_id)
);

-- ******************************************
-- Junction tables
--
-- fragrance_perfumer -> A fragrance is created by one or more perfumers
-- fragrance_concentration -> A fragrance may have one or more versions
-- fragrance_notes -> A fragrance composition consist of multiple notes
-- perfumer_brand -> A perfumer may work with one or multiple brands

CREATE TABLE fragrance_perfumer
(
    fragrance_id INT,
    perfumer_id  INT,
    PRIMARY KEY (fragrance_id, perfumer_id),
    FOREIGN KEY (fragrance_id) REFERENCES fragrance (fragrance_id),
    FOREIGN KEY (perfumer_id) REFERENCES perfumer (perfumer_id)
);

CREATE TABLE fragrance_concentration
(
    fragrance_id     INT,
    concentration_id INT,
    PRIMARY KEY (fragrance_id, concentration_id),
    FOREIGN KEY (fragrance_id) REFERENCES fragrance (fragrance_id) ON DELETE CASCADE,
    FOREIGN KEY (concentration_id) REFERENCES concentration (concentration_id) ON DELETE CASCADE
);


CREATE TABLE perfumer_brand
(
    perfumer_id INT,
    brand_id    INT,
    PRIMARY KEY (perfumer_id, brand_id),
    FOREIGN KEY (perfumer_id) REFERENCES perfumer (perfumer_id) ON DELETE CASCADE,
    FOREIGN KEY (brand_id) REFERENCES brand (brand_id) ON DELETE CASCADE
);


CREATE TABLE fragrance_notes
(
    fragrance_id INT,
    note_id      INT,
    PRIMARY KEY (fragrance_id, note_id),
    FOREIGN KEY (fragrance_id) REFERENCES fragrance (fragrance_id) ON DELETE CASCADE,
    FOREIGN KEY (note_id) REFERENCES note (note_id) ON DELETE CASCADE
);
