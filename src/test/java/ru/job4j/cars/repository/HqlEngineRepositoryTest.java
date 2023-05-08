package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlEngineRepositoryTest {

    private static HqlEngineRepository store;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlEngineRepository(cr);
    }

    @Test
    void whenAddAndFindById() {
        Engine engine = new Engine();

        store.add(engine);
        Optional<Engine> engineOptional = store.findById(engine.getId());

        assertThat(engineOptional.isPresent()).isTrue();
        assertThat(engineOptional.get().getCharactValue()).isNotEqualTo(0);
    }

    @Test
    void whenFindAll() {
        store.truncateTable();
        Engine engine = new Engine();
        store.add(engine);
        Engine engine2 = new Engine();
        store.add(engine2);

        List<Engine> engineList = store.findAll();
        engineList.forEach(System.out::println);
        assertThat(engineList).isNotEmpty().hasSize(2).contains(engine2, engine);
    }

    @Test
    void whenDelete() {
        Engine engine = new Engine();
        store.add(engine);
        store.delete(engine);
        Optional<Engine> engineOptional = store.findById(engine.getId());
        assertThat(engineOptional).isEmpty();
    }
}