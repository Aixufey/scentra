package com.rinseo.scentra.repository;

import com.rinseo.scentra.model.Fragrance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class FragranceRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    FragranceRepository repo;
    @Test
    void testFindFragrance_WhenNameGiven_returnsFragranceEntity() {
        // Arrange
        Fragrance alexandriaII = new Fragrance();
        Fragrance alexandriaIII = new Fragrance();


        alexandriaII.setName("Alexandria II");
        alexandriaII.setYear(2012);

        alexandriaIII.setName("Alexandria III");
        alexandriaIII.setYear(2019);

        entityManager.persistAndFlush(alexandriaII);
        entityManager.persistAndFlush(alexandriaIII);

        // Act
        List<Fragrance> fragrances = repo.findFragrancesByNameContainsIgnoreCase("Alex");

        List<Fragrance> fragrancesStartsWithA = repo.findFragrancesStartsWithA();

        Fragrance fragrance = repo.findByName("Alexandria III");

        // Assert
        Assertions.assertFalse(fragrances.isEmpty());

        Assertions.assertEquals("Alexandria II", fragrances.stream().findFirst().get().getName());

        Assertions.assertTrue(fragrancesStartsWithA.stream().allMatch(frag -> frag.getName().startsWith("A")));

        Assertions.assertEquals("Alexandria III", fragrance.getName());
    }
}
