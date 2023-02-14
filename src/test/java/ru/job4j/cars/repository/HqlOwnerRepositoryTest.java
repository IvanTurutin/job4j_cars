package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlOwnerRepositoryTest {

    private static HqlOwnerRepository store;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlOwnerRepository(cr);
    }

    @Test
    void whenAddAndFindById() {
        Owner owner = new Owner();
        owner.setName("Name");
        store.add(owner);
        Optional<Owner> optionalOwner = store.findById(owner.getId());

        assertThat(optionalOwner).isPresent();
        assertThat(optionalOwner.get().getId()).isNotEqualTo(0);
        assertThat(optionalOwner.get().getName()).isEqualTo("Name");
    }

    @Test
    void whenFindAll() {
        Owner owner = new Owner();
        owner.setName("Name");
        store.add(owner);
        Owner owner2 = new Owner();
        owner2.setName("Name2");
        store.add(owner2);

        List<Owner> owners = store.findAll();
        owners.forEach(System.out::println);
        assertThat(owners).isNotEmpty().hasSize(2).contains(owner, owner2);
    }

    @Test
    void whenUpdate() {
        Owner owner = new Owner();
        owner.setName("Name");
        store.add(owner);
        int id = owner.getId();
        owner.setName("Updated name");
        store.update(owner);

        Optional<Owner> ownerDb = store.findById(owner.getId());
        assertThat(ownerDb).isPresent();
        assertThat(ownerDb.get().getName()).isEqualTo("Updated name");
        assertThat(ownerDb.get().getId()).isEqualTo(id);
    }

    @Test
    void whenDelete() {
        Owner owner = new Owner();
        owner.setName("Name");
        store.add(owner);

        store.delete(owner);
        Optional<Owner> ownerDb = store.findById(owner.getId());
        assertThat(ownerDb).isEmpty();
    }
}