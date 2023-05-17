package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.*;
import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*@SpringBootTest*/
class HqlCarRepositoryTest {

    private static HqlCarRepository store;
    private static HqlOwnerRepository ownerRepository;
    private static Engine engine;
    private static Body body;
    private static CarModel carModel;
    private static Transmission transmission;
    private static Owner owner;
    private static Owner owner2;
    private static CarOwner carOwner;
    private static CarOwner carOwner2;
    private static List<CarOwner> owners;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlCarRepository(cr);
        store.truncateTable();
        ownerRepository = new HqlOwnerRepository(cr);
        ownerRepository.truncateTable();

        HqlEngineRepository engineRepository = new HqlEngineRepository(cr);
        engineRepository.truncateTable();
        engine = new Engine();
        engine.setName("Engine 1");

        HqlOwnerRepository ownerRepository = new HqlOwnerRepository(cr);
        ownerRepository.truncateTable();
        owner = new Owner();
        owner.setName("Owner 1");
        ownerRepository.add(owner);
        owner2 = new Owner();
        owner2.setName("Owner 2");
        ownerRepository.add(owner2);

        carOwner = new CarOwner();
        carOwner.setOwner(owner);
        carOwner.setStartAt(LocalDateTime.now().minusYears(1).withNano(0));
        carOwner.setEndAt(LocalDateTime.now().minusMonths(6).withNano(0));

        carOwner2 = new CarOwner();
        carOwner2.setOwner(owner2);
        carOwner2.setStartAt(LocalDateTime.now().minusMonths(6).withNano(0));
        carOwner2.setEndAt(LocalDateTime.now().withNano(0));

        owners = new ArrayList<>();
        owners.add(carOwner);
        owners.add(carOwner2);

        body = new Body();
        body.setName("Body1");
        carModel = new CarModel();
        carModel.setName("CarModel1");
        transmission = new Transmission();
        transmission.setName("Transmission1");
    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
    }

    @Test
    void whenAddAndFindById() {
        Car car = new Car();
        car.setBody(body);
        car.setCarModel(carModel);
        car.setTransmission(transmission);
        car.setEngine(engine);
        car.setOwner(owner);
        car.setCarOwners(owners);
        carOwner.setCar(car);
        carOwner2.setCar(car);

        System.out.println("car before add = " + car);
        store.add(car);
        System.out.println("car after add = " + car);
        Optional<Car> carInDb = store.findById(car.getId());

        assertThat(carInDb.isPresent()).isTrue();
        assertThat(carInDb.get().getId()).isNotEqualTo(0);
        assertThat(carInDb.get().getBody().getName()).isEqualTo("Body1");
        assertThat(carInDb.get().getCarModel().getName()).isEqualTo("CarModel1");
        assertThat(carInDb.get().getTransmission().getName()).isEqualTo("Transmission1");
        assertThat(carInDb.get().getOwner().getName()).isEqualTo("Owner 1");
        assertThat(carInDb.get().getEngine().getName()).isEqualTo("Engine 1");
        assertThat(carInDb.get().getCarOwners()).isNotEmpty().hasSize(2).contains(carOwner, carOwner2);
    }


    @Test
    void whenFindAll() {
        Car car1 = new Car();
        car1.setEngine(new Engine());
        car1.setOwner(owner);
        car1.setCarOwners(List.of(new CarOwner()));

        store.add(car1);
        System.out.println("car1 = " + car1);

        Car car2 = new Car();
        car2.setEngine(new Engine());
        car2.setOwner(owner);
        car2.setCarOwners(List.of(new CarOwner()));

        System.out.println("car2 = " + car2);
        store.add(car2);
        System.out.println("car2 = " + car2);

        List<Car> carsFromDb = store.findAll();

        assertThat(carsFromDb).isNotEmpty().hasSize(2).contains(car1, car2);

    }

    @Test
    void whenUpdate() {
        Car car1 = new Car();
        car1.setEngine(engine);
        car1.setOwner(owner);
        CarOwner carOwner3 = new CarOwner(owner, car1);

        car1.setCarOwners(List.of(carOwner3));

        store.add(car1);

        System.out.println("car1 at whenUpdate() = " + car1);

        Car car2 = new Car();
        car2.setOwner(owner2);
        store.add(car2);

        car1.setOwner(owner2);
        CarOwner carOwner4 = new CarOwner(owner2, car1);
        car1.setCarOwners(List.of(carOwner3, carOwner4));
        ownerRepository.add(carOwner4.getOwner());

        store.update(car1);

        Optional<Car> carInDb = store.findById(car1.getId());

        System.out.println("car1 at whenUpdate() after update = " + car1);
        System.out.println("carOwner4 after update = " + carOwner4);

        assertThat(carInDb.isPresent()).isTrue();
        System.out.println("car1 at whenUpdate() findById object = " + carInDb.get());

        assertThat(carInDb.get().getId()).isNotEqualTo(0);
        assertThat(carInDb.get().getOwner().getName()).isEqualTo("Owner 2");
        assertThat(carInDb.get().getCarOwners()).isNotEmpty().hasSize(2);
        assertThat(carInDb.get().getCarOwners().get(0).getEndAt().toLocalDate()).isEqualTo(carOwner3.getEndAt().toLocalDate());
        assertTrue(carInDb.get().getCarOwners().contains(carOwner3));
        assertThat(carInDb.get().getCarOwners().get(1).getEndAt().toLocalDate()).isEqualTo(carOwner4.getEndAt().toLocalDate());
        assertThat(carInDb.get().getCarOwners().get(1).getOwner()).isEqualTo(owner2);
        carOwner4 = carInDb.get().getCarOwners().get(1);
        System.out.println("car1 at whenUpdate() before delete carOwner = " + car1);
        System.out.println("carOwner4 at whenUpdate() before delete carOwner = " + carOwner4);

        car1.setCarOwners(new ArrayList<>(List.of(carOwner3)));
        store.update(car1);
        System.out.println("car1 at whenUpdate() after delete carOwner = " + car1);
        carInDb = store.findById(car1.getId());
        System.out.println("carInDb at whenUpdate() after delete carOwner = " + car1);

        assertThat(carInDb.isPresent()).isTrue();
        assertThat(carInDb.get().getCarOwners()).isNotEmpty().hasSize(1);
        assertThat(carInDb.get().getCarOwners().get(0).getEndAt().toLocalDate()).isEqualTo(carOwner3.getEndAt().toLocalDate());
        assertTrue(carInDb.get().getCarOwners().contains(carOwner3));
        assertFalse(carInDb.get().getCarOwners().contains(carOwner4));
    }

    @Test
    void whenDelete() {
        Car car1 = new Car();
        car1.setEngine(new Engine());
        car1.setOwner(new Owner());
        car1.setCarOwners(List.of(new CarOwner()));

        store.add(car1);
        Optional<Car> fromDb = store.findById(car1.getId());
        assertThat(fromDb).isPresent();
        store.delete(fromDb.get());
        List<Car> carsFromDb = store.findAll();
        assertThat(carsFromDb).isEmpty();
    }
}