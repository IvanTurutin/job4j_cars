package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/*@SpringBootTest*/
class HqlCarRepositoryTest {

    private static HqlCarRepository store;
    private static Engine engine;
    private static Owner owner;
    private static Owner owner2;
    private static List<Owner> owners;


    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlCarRepository(cr);
        store.truncateTable();

        HqlEngineRepository engineRepository = new HqlEngineRepository(cr);
        engineRepository.truncateTable();
        engine = new Engine();

        HqlOwnerRepository ownerRepository = new HqlOwnerRepository(cr);
        ownerRepository.truncateTable();
        owner = new Owner();
        owner.setName("Owner 1");
        owner2 = new Owner();
        owner2.setName("Owner 2");

        owners = new ArrayList<>();
        owners.add(owner);
        owners.add(owner2);

    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
    }

    @Test
    void whenAddAndFindById() {
        Car car = new Car();
        car.setName("car1");

        car.setEngine(engine);
        car.setOwner(owner);
        car.setOwners(owners);

        store.add(car);
        System.out.println("car = " + car);
        Optional<Car> carInDb = store.findById(car.getId());

        assertThat(carInDb.isPresent()).isTrue();
        assertThat(carInDb.get().getId()).isNotEqualTo(0);
        assertThat(carInDb.get().getName()).isEqualTo("car1");
        assertThat(carInDb.get().getOwner().getName()).isEqualTo("Owner 1");
        assertThat(carInDb.get().getEngine()).isEqualTo(engine);
        assertThat(carInDb.get().getOwners()).isNotEmpty().hasSize(2).contains(owner, owner2);
    }


    @Test
    void whenFindAll() {
        Car car1 = new Car();
        car1.setName("car1");
        car1.setEngine(new Engine());
        car1.setOwner(new Owner());
        car1.setOwners(List.of(new Owner()));

        store.add(car1);
        System.out.println("car1 = " + car1);

        Car car2 = new Car();
        car2.setName("car2");
        car2.setEngine(new Engine());
        car2.setOwner(new Owner());
        car2.setOwners(List.of(new Owner()));

        System.out.println("car2 = " + car2);
        store.add(car2);
        System.out.println("car2 = " + car2);

        List<Car> carsFromDb = store.findAll();

        assertThat(carsFromDb).isNotEmpty().hasSize(2).contains(car1, car2);

    }

    @Test
    void whenUpdate() {
        Car car1 = new Car();
        car1.setName("car1");
        car1.setEngine(new Engine());
        car1.setOwner(new Owner());
        car1.setOwners(List.of(new Owner()));

        store.add(car1);

        car1.setName("car1_upd");

        store.update(car1);

        Optional<Car> carInDb = store.findById(car1.getId());

        assertThat(carInDb.isPresent()).isTrue();
        assertThat(carInDb.get().getId()).isNotEqualTo(0);
        assertThat(carInDb.get().getName()).isEqualTo("car1_upd");

    }

    @Test
    void whenDelete() {
        Car car1 = new Car();
        car1.setName("car1");
        car1.setEngine(new Engine());
        car1.setOwner(new Owner());
        car1.setOwners(List.of(new Owner()));

        store.add(car1);
        Optional<Car> fromDb = store.findById(car1.getId());
        assertThat(fromDb).isPresent();
        store.delete(fromDb.get());

        List<Car> carsFromDb = store.findAll();

        assertThat(carsFromDb).isEmpty();
    }
}