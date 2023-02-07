package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.Main;
import ru.job4j.cars.model.Engine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlEngineRepositoryTest {

    private static HqlEngineRepository store;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new Main().sf());
        store = new HqlEngineRepository(cr);
        store.truncateTable();
    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
    }
    @Test
    void add() {
        Engine engine = new Engine();

        store.add(engine);
        Optional<Engine> engineOptional = store.findById(engine.getId());

        assertThat(engineOptional.isPresent()).isTrue();
        assertThat(engineOptional.get().getId()).isNotEqualTo(0);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }
}