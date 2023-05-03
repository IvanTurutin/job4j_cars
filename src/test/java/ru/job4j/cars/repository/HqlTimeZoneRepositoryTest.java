package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.TimeZone;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlTimeZoneRepositoryTest {

    private static HqlTimeZoneRepository store;
    private static TimeZone timeZone;
    private static TimeZone timeZone2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlTimeZoneRepository(cr);

        timeZone = new TimeZone();
        timeZone.setZoneId("UTC+1");
        timeZone2 = new TimeZone();
        timeZone2.setZoneId("UTC+2");
    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
    }

    @Test
    void whenAddAndFindById() {
        store.add(timeZone);
        Optional<TimeZone> timeZoneOptional = store.findById(timeZone.getId());

        assertThat(timeZoneOptional.isPresent()).isTrue();
        assertThat(timeZoneOptional.get().getId()).isNotEqualTo(0);
    }

    @Test
    void findAll() {
        store.add(timeZone);
        store.add(timeZone2);

        List<TimeZone> timeZoneList = store.findAll();
        timeZoneList.forEach(System.out::println);
        assertThat(timeZoneList).isNotEmpty().hasSize(2).contains(timeZone, timeZone2);

    }

    @Test
    void update() {
        store.add(timeZone);
        timeZone.setZoneId("UTC+3");
        store.update(timeZone);
        Optional<TimeZone> timeZoneOptional = store.findById(timeZone.getId());
        assertThat(timeZoneOptional).isPresent();
        assertThat(timeZoneOptional.get().getZoneId()).isEqualTo("UTC+3");
    }

    @Test
    void whenDouble() {
        store.add(new TimeZone("UTC+1"));
        store.add(new TimeZone("UTC+1"));
        List<TimeZone> timeZoneList = store.findAll();
        assertThat(timeZoneList).isNotEmpty().hasSize(1);
        assertThat(timeZoneList.get(0).getZoneId()).isEqualTo("UTC+1");
    }

    @Test
    void delete() {
        store.add(timeZone);
        store.delete(timeZone);
        Optional<TimeZone> timeZoneOptional = store.findById(timeZone.getId());
        assertThat(timeZoneOptional).isEmpty();

    }
}