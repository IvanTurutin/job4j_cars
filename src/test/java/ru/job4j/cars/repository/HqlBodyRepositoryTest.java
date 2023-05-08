package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlBodyRepositoryTest {

    private static HqlBodyRepository store;
    private static Body body;
    private static Body body2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlBodyRepository(cr);

        body = new Body();
        body.setName("Body1");
        body2 = new Body();
        body2.setName("Body2");

    }

    @Test
    void whenAddAndFindById() {

        store.add(body);
        Optional<Body> engineOptional = store.findById(body.getId());

        assertThat(engineOptional.isPresent()).isTrue();
        assertThat(engineOptional.get().getCharactValue()).isNotEqualTo(0);
    }

    @Test
    void whenFindAll() {
        store.add(body);
        store.add(body2);

        List<Body> engineList = store.findAll();
        engineList.forEach(System.out::println);
        assertThat(engineList).isNotEmpty().hasSize(2).contains(body, body2);
    }

    @Test
    void whenDelete() {
        store.add(body);
        store.delete(body);
        Optional<Body> engineOptional = store.findById(body.getId());
        assertThat(engineOptional).isEmpty();
    }
}