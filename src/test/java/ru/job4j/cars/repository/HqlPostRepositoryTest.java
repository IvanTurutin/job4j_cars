package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HqlPostRepositoryTest {

    private static HqlPostRepository store;
    private static Post post;
    private static Engine engine;
    private static Owner owner;
    private static Owner owner2;
    private static PriceHistory priceHistory;
    private static PriceHistory priceHistory2;
    private static User user;
    private static User user2;
    private static User user3;
    private static File file;
    private static File file2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlPostRepository(cr);
        store.truncateTable();

    }

    @BeforeEach
    public void createEntity() {
        post = new Post();
        post.setText("Post1");
        user = new User();
        user.setLogin("User1");
        post.setUser(user);

        Car car = new Car();
        car.setName("Car1");
        engine = new Engine();
        car.setEngine(engine);
        owner = new Owner();
        owner.setName("Owner1");
        car.setOwner(owner);
        owner2 = new Owner();
        owner2.setName("Owner2");
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);
        owners.add(owner2);
        car.setOwners(owners);
        post.setCar(car);

        priceHistory = new PriceHistory();
        priceHistory.setBefore(100);
        priceHistory.setAfter(90);
        priceHistory.setCreated(LocalDateTime.now().minusDays(2));
        priceHistory2 = new PriceHistory();
        priceHistory2.setBefore(90);
        priceHistory2.setAfter(85);
        priceHistory2.setCreated(LocalDateTime.now().minusDays(1));
        List<PriceHistory> priceHistories = List.of(priceHistory, priceHistory2);
        post.setPriceHistory(priceHistories);

        user2 = new User();
        user2.setLogin("User2");
        user3 = new User();
        user3.setLogin("User3");
        List<User> users = List.of(user2, user3);
        post.setUsers(users);

        file = new File();
        file.setName("File1");
        file2 = new File();
        file2.setName("File2");
        ArrayList<File> files = new ArrayList<>(List.of(file, file2));
        post.setFiles(files);

    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
    }

    @Test
    void whenAdd() {
        System.out.println("post = " + post);

        store.add(post);
        System.out.println("post = " + post);

        Optional<Post> postFromDb = store.findById(post.getId());
        System.out.println("postFromDb = " + postFromDb);
        assertThat(postFromDb.isPresent()).isTrue();
        assertThat(postFromDb.get().getId()).isNotEqualTo(0);
        assertThat(postFromDb.get().getText()).isEqualTo("Post1");
        assertThat(postFromDb.get().getUser().getLogin()).isEqualTo("User1");
        assertThat(postFromDb.get().getCar().getName()).isEqualTo("Car1");
        assertThat(postFromDb.get().getCar().getEngine().getId()).isEqualTo(engine.getId());
        assertThat(postFromDb.get().getCar().getOwner().getName()).isEqualTo("Owner1");
        assertThat(postFromDb.get().getCar().getOwners()).isNotEmpty().hasSize(2).contains(owner, owner2);
        assertThat(postFromDb.get().getPriceHistory()).isNotEmpty().hasSize(2).contains(priceHistory, priceHistory2);
        assertThat(postFromDb.get().getFiles()).isNotEmpty().hasSize(2).contains(file, file2);
        assertThat(postFromDb.get().getUsers()).isNotEmpty().hasSize(2).contains(user2, user3);
    }

    @Test
    void whenUpdate() {
        System.out.println(post);
        store.add(post);
        System.out.println(post);
        post.setText("Updated");
        File file3 = new File();
        file3.setName("File3");
        post.getFiles().add(file3);
        System.out.println(post);
        store.update(post);
        System.out.println(post);

        Optional<Post> postFromDb = store.findById(post.getId());
        System.out.println(postFromDb);
        assertThat(postFromDb.isPresent()).isTrue();
        assertThat(postFromDb.get().getText()).isEqualTo("Updated");
        assertThat(postFromDb.get().getFiles()).isNotEmpty().hasSize(3);
    }

    @Test
    void whenDelete() {
        System.out.println(post);
        store.add(post);
        System.out.println(post);
        store.delete(post.getId());
        List<Post> posts = store.findAllOrderById();
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindAllOrderById() {
        System.out.println(post);
        store.add(post);
        System.out.println(post);

        List<Post> posts = store.findAllOrderById();
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
    }

    @Test
    void whenFindByUser() {
        System.out.println(post);
        store.add(post);
        System.out.println(post);

        List<Post> postOptional = store.findByUser(user);
        postOptional.forEach(System.out::println);
        assertThat(postOptional).isNotEmpty().hasSize(1).contains(post);

        postOptional = store.findByUser(user2);
        assertThat(postOptional).isEmpty();
    }

    @Test
    void whenFindLastDays() {
        post.setCreated(LocalDateTime.now().minusDays(2));
        store.add(post);

        List<Post> posts = store.findLastDays(3);
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);

        posts = store.findLastDays(1);
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindWithPhoto() {
        store.add(post);
        List<Post> posts = store.findWithPhoto();
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        post.setFiles(new ArrayList<>());
        store.update(post);
        posts = store.findWithPhoto();
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindCarName() {
        store.add(post);
        List<Post> posts = store.findCarName("Car1");
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        posts = store.findCarName("Car2");
        assertThat(posts).isEmpty();
    }
}