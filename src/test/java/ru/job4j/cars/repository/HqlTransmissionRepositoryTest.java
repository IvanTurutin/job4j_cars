package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlTransmissionRepositoryTest {

    private static HqlTransmissionRepository store;
    private static Transmission transmission;
    private static Transmission transmission2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlTransmissionRepository(cr);

        transmission = new Transmission();
        transmission.setName("Transmission1");
        transmission2 = new Transmission();
        transmission2.setName("Transmission2");

    }

    @Test
    void whenAddAndFindById() {

        store.add(transmission);
        Optional<Transmission> engineOptional = store.findById(transmission.getId());

        assertThat(engineOptional.isPresent()).isTrue();
        assertThat(engineOptional.get().getId()).isNotEqualTo(0);
    }

    @Test
    void whenFindAll() {
        store.add(transmission);
        store.add(transmission2);

        List<Transmission> engineList = store.findAll();
        engineList.forEach(System.out::println);
        assertThat(engineList).isNotEmpty().hasSize(2).contains(transmission, transmission2);
    }

    @Test
    void whenDelete() {
        store.add(transmission);
        store.delete(transmission);
        Optional<Transmission> engineOptional = store.findById(transmission.getId());
        assertThat(engineOptional).isEmpty();
    }
}