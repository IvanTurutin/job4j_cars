package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlUserRepositoryTest {

    private static HqlUserRepository store;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlUserRepository(cr);
    }


    @Test
    void whenCreateAndFindById() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        store.create(user);
        Optional<User> userDb = store.findById(user.getId());
        assertThat(userDb).isPresent();
        System.out.println(userDb.get());
        assertThat(userDb.get().getLogin()).isEqualTo("Login");
        assertThat(userDb.get().getPassword()).isEqualTo("Password");
    }

    @Test
    void whenUpdate() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        store.create(user);
        user.setLogin("Updated login");
        user.setPassword("Updated password");
        store.update(user);
        Optional<User> userDb = store.findById(user.getId());
        assertThat(userDb).isPresent();
        assertThat(userDb.get().getLogin()).isEqualTo("Updated login");
        assertThat(userDb.get().getPassword()).isEqualTo("Updated password");

    }

    @Test
    void whenDelete() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        store.create(user);
        store.delete(user.getId());
        Optional<User> userDb = store.findById(user.getId());
        assertThat(userDb).isEmpty();

    }

    @Test
    void whenFindAllOrderById() {
        store.truncate();
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        store.create(user);
        User user2 = new User();
        user2.setLogin("Login2");
        user2.setPassword("Password2");
        store.create(user2);

        List<User> users = store.findAllOrderById();
        users.forEach(System.out::println);
        assertThat(users).isNotEmpty().hasSize(2).contains(user, user2);
    }

    @Test
    void findByLikeLogin() {
        store.truncate();
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        store.create(user);
        User user2 = new User();
        user2.setLogin("Login2");
        user2.setPassword("Password2");
        store.create(user2);

        List<User> users1 = store.findByLikeLogin("ogin");
        assertThat(users1).isNotEmpty().hasSize(2).contains(user, user2);
        List<User> users2 = store.findByLikeLogin("Login2");
        assertThat(users2).isNotEmpty().hasSize(1).contains(user2);
        List<User> users3 = store.findByLikeLogin("Iogin");
        assertThat(users3).isEmpty();
    }

    @Test
    void whenFindByLoginAndPassword() {
        store.truncate();
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        store.create(user);
        User user2 = new User();
        user2.setLogin("Login2");
        user2.setPassword("Password2");
        store.create(user2);

        Optional<User> userDb = store.findByLoginAndPassword("Login", "Password");
        assertThat(userDb).isPresent();
        assertThat(userDb.get().getLogin()).isEqualTo("Login");
        assertThat(userDb.get().getPassword()).isEqualTo("Password");
    }
}