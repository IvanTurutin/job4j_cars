package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.Main;
import ru.job4j.cars.model.Owner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlOwnerRepositoryTest {

    private static HqlOwnerRepository store;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new Main().sf());
        store = new HqlOwnerRepository(cr);
        store.truncateTable();
    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
    }

    @Test
    void add() {
        Owner owner = new Owner();
        owner.setName("Name");

        store.add(owner);
        Optional<Owner> optionalOwner = store.findById(owner.getId());

        assertThat(optionalOwner.isPresent()).isTrue();
        assertThat(optionalOwner.get().getId()).isNotEqualTo(0);
        assertThat(optionalOwner.get().getName()).isEqualTo("Name");

    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}