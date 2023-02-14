package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.File;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlFileRepositoryTest {

    private static HqlFileRepository store;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlFileRepository(cr);
    }

    @Test
    void whenSaveAndFindById() {
        File file = new File();
        file.setName("File1");
        file.setPath("directory");
        store.save(file);
        Optional<File> fileDb = store.findById(file.getId());
        System.out.println(fileDb);
        assertThat(fileDb).isPresent();
        assertThat(fileDb.get().getName()).isEqualTo("File1");
        assertThat(fileDb.get().getPath()).isEqualTo("directory");

    }

    @Test
    void whenDeleteById() {
        File file = new File();
        file.setName("File1");
        file.setPath("directory");
        store.save(file);
        store.deleteById(file.getId());
        Optional<File> fileDb = store.findById(file.getId());
        assertThat(fileDb).isEmpty();

    }
}