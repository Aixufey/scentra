package com.rinseo.scentra.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

// Testing Data Layer, Spring will configure an in-memory database, scan for an @Entity, @Table and create a schema.
// We need to use a persistence context(Repo) to interact with the database such as TestEntityManager.
// If there is a data.sql file, make sure to terminate it in application.properties. spring.sql.init.mode=never
@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never") // Disable data.sql higher precedence
class FragranceEntityIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Can create a Fragrance")
    void testFragranceEntity_whenValidEntity_thenReturnStoredEntity() {
        // Arrange
        Fragrance santal33 = new Fragrance();
        santal33.setName("Santal 33");
        santal33.setYear(2011);

        // Act
        Fragrance createdEntity = entityManager.persistAndFlush(santal33);

        // Assert
        Assertions.assertTrue(createdEntity.getId() > 0);

        Assertions.assertEquals(santal33.getName(), createdEntity.getName());
    }

    @Test
    @DisplayName("Duplicate Fragrance not allowed")
    void testFragrance_whenDuplicateName_thenThrowException() {
        // Arrange
        Fragrance santal33 = new Fragrance();
        santal33.setName("Santal 33");
        santal33.setYear(2011);

        entityManager.persistAndFlush(santal33);

        // Act
        Fragrance duplicate = new Fragrance();
        duplicate.setName("Santal 33");
        duplicate.setYear(2011);

        // Assert
        assertThrows(Exception.class, () -> entityManager.persistAndFlush(duplicate));
    }
}
