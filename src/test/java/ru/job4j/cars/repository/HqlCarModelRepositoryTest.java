package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlCarModelRepositoryTest {

    private static HqlCarModelRepository store;
    private static CarModel carModel;
    private static CarModel carModel2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlCarModelRepository(cr);

        carModel = new CarModel();
        carModel.setName("CarModel1");
        carModel2 = new CarModel();
        carModel2.setName("CarModel2");

    }

    @Test
    void whenAddAndFindById() {

        store.add(carModel);
        Optional<CarModel> engineOptional = store.findById(carModel.getId());

        assertThat(engineOptional.isPresent()).isTrue();
        assertThat(engineOptional.get().getCharactValue()).isNotEqualTo(0);
    }

    @Test
    void whenFindAll() {
        store.add(carModel);
        store.add(carModel2);

        List<CarModel> engineList = store.findAll();
        engineList.forEach(System.out::println);
        assertThat(engineList).isNotEmpty().hasSize(2).contains(carModel, carModel2);
    }

    @Test
    void whenDelete() {
        store.add(carModel);
        store.delete(carModel);
        Optional<CarModel> engineOptional = store.findById(carModel.getId());
        assertThat(engineOptional).isEmpty();
    }
}