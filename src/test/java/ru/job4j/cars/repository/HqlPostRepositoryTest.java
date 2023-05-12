package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.*;
import ru.job4j.cars.model.TimeZone;
import ru.job4j.cars.searchattributes.PostIsPublish;
import ru.job4j.cars.searchattributes.SearchAttribute;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HqlPostRepositoryTest {

    private static HqlPostRepository store;
    private static HqlTimeZoneRepository storeTimeZone;
    private static HqlUserRepository storeUser;
    private static HqlOwnerRepository ownerRepository;
    private static Post post;
    private static Engine engine;
    private static CarOwner carOwner;
    private static CarOwner carOwner2;
    private static PriceHistory priceHistory;
    private static PriceHistory priceHistory2;
    private static User user;
    private static User user2;
    private static User user3;
    private static File file;
    private static File file2;
    private static CarModel carModel;
    private static Body body;
    private static Transmission transmission;
    private static Car car;
    private TimeZone timeZone;
    private TimeZone timeZone2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlPostRepository(cr);
        store.truncateTable();

        storeTimeZone = new HqlTimeZoneRepository(cr);

        storeUser = new HqlUserRepository(cr);

        ownerRepository = new HqlOwnerRepository(cr);
        ownerRepository.truncateTable();

    }

    @BeforeEach
    public void createEntity() {
        post = new Post();
        post.setText("Post1");
        timeZone = new TimeZone("UTC+1");
        user = new User("User1", "pass1", "UserName1", timeZone);
        /*user.setLogin("User1");*/
        post.setUser(user);

        body = new Body();
        body.setName("Body1");
        carModel = new CarModel();
        carModel.setName("CarModel1");
        transmission = new Transmission();
        transmission.setName("Transmission1");
        car = new Car();
        engine = new Engine();
        car.setEngine(engine);
        car.setBody(body);
        car.setCarModel(carModel);
        car.setTransmission(transmission);
        Owner owner = new Owner();
        owner.setName("Owner1");
        ownerRepository.add(owner);
        car.setOwner(owner);
        Owner owner2 = new Owner();
        owner2.setName("Owner2");
        ownerRepository.add(owner2);

        carOwner = new CarOwner();
        carOwner.setOwner(owner);
        carOwner.setCar(car);
        carOwner2 = new CarOwner();
        carOwner2.setOwner(owner2);
        carOwner2.setCar(car);

        List<CarOwner> owners = new ArrayList<>();
        /*Set<CarOwner> owners = new HashSet<>();*/
        owners.add(carOwner);
        owners.add(carOwner2);
        car.setCarOwners(owners);
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

        timeZone2 = new TimeZone("UTC+2");
        user2 = new User("User2", "pass2", "UserName2", timeZone2);
        TimeZone timeZone3 = new TimeZone("UTC+3");
        user3 = new User("User3", "pass3", "UserName3", timeZone3);
        List<User> users = List.of(user2, user3);
        post.setUsers(users);

        file = new File();
        file.setName("File1");
        file.setPath("files/");
        file2 = new File();
        file2.setName("File2");
        file2.setPath("files/");

        ArrayList<File> files = new ArrayList<>(List.of(file, file2));
        post.setFiles(files);

        post.setPublish(true);
        System.out.println("Post at beforeEach() = " + post);

    }

    @AfterEach
    public void truncateTable() {
        store.truncateTable();
        storeUser.truncate();
        storeTimeZone.truncateTable();
        ownerRepository.truncateTable();
    }

    @Test
    void whenAdd() {
        System.out.println("post = " + post);

        store.add(post);
        System.out.println("post after add() = " + post);
        System.out.println("user after add() = " + user);
        System.out.println("timezone after add() = " + timeZone);

        Optional<Post> postFromDb = store.findById(post.getId());
        System.out.println("postFromDb = " + postFromDb);
        assertThat(postFromDb.isPresent()).isTrue();
        assertThat(postFromDb.get().getId()).isNotEqualTo(0);
        assertThat(postFromDb.get().getText()).isEqualTo("Post1");
        assertThat(postFromDb.get().getUser().getLogin()).isEqualTo("User1");
        assertThat(postFromDb.get().getCar().getBody().getName()).isEqualTo("Body1");
        assertThat(postFromDb.get().getCar().getEngine().getCharactValue()).isEqualTo(engine.getCharactValue());
        assertThat(postFromDb.get().getCar().getCarModel().getName()).isEqualTo("CarModel1");
        assertThat(postFromDb.get().getCar().getTransmission().getName()).isEqualTo("Transmission1");
        assertThat(postFromDb.get().getCar().getOwner().getName()).isEqualTo("Owner1");
        assertThat(postFromDb.get().getCar().getCarOwners()).isNotEmpty().hasSize(2);
        /*assertThat(postFromDb.get().getCar().getCarOwners().get(0).getStartAt().toLocalDate()).isEqualTo(carOwner.getStartAt().toLocalDate());*/
        assertTrue(postFromDb.get().getCar().getCarOwners().contains(carOwner));
        /*assertThat(postFromDb.get().getCar().getCarOwners().get(1).getStartAt().toLocalDate()).isEqualTo(carOwner2.getStartAt().toLocalDate());*/
        assertTrue(postFromDb.get().getCar().getCarOwners().contains(carOwner2));
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
        post.getCar().getCarModel().setName("Updated CarModel1");
        post.getCar().getOwner().setName("Updated Owner1");
        Owner owner5 = new Owner();
        owner5.setName("Owner5");
        post.getCar().setOwner(owner5);
        file3.setName("File3");
        post.getFiles().add(file3);
        System.out.println(post);
        store.update(post);
        System.out.println(post);

        Optional<Post> postFromDb = store.findById(post.getId());
        System.out.println(postFromDb);
        assertThat(postFromDb.isPresent()).isTrue();
        assertThat(postFromDb.get().getCar().getCarModel().getName()).isEqualTo("Updated CarModel1");
        assertThat(postFromDb.get().getCar().getOwner().getName()).isEqualTo("Owner5");
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
        List<Post> posts = store.findBySearchAttributes(List.of(new File()));
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        post.setFiles(new ArrayList<>());
        store.update(post);
        posts = store.findBySearchAttributes(List.of(new File()));
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindByCarModel() {
        List<User> users = storeUser.findAll();
        users.forEach(System.out::println);

        List<TimeZone> timeZones = storeTimeZone.findAll();
        timeZones.forEach(storeTimeZone::delete);
        timeZones.forEach(System.out::println);

        System.out.println("timeZone at whenFindByCarModel = " + timeZone2);
        post.getUser().setTimeZone(timeZone2);
        store.add(post);
        List<Post> posts = store.findBySearchAttributes(List.of(carModel));
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1);
        assertThat(posts.get(0).getCar().getCarModel().getName()).isEqualTo(carModel.getName());
        /*assertThat(posts.get(0).getCar().getCarModel().getName()).isEqualTo("CarModel1");*/

        CarModel carModel2 = new CarModel();
        carModel2.setName("CarModel2");
        posts = store.findBySearchAttributes(List.of(carModel2));
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindByBody() {

        store.add(post);
        List<Post> posts = store.findBySearchAttributes(List.of(body));
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        assertThat(posts.get(0).getCar().getBody().getName()).isEqualTo("Body1");
        Body body2 = new Body();
        body2.setName("Body2");
        posts = store.findBySearchAttributes(List.of(body2));
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindByTransmission() {
        store.add(post);
        List<Post> posts = store.findBySearchAttributes(List.of(transmission));
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        assertThat(posts.get(0).getCar().getTransmission().getName()).isEqualTo("Transmission1");
        Transmission transmission2 = new Transmission();
        transmission2.setName("Transmission2");
        posts = store.findBySearchAttributes(List.of(transmission2));
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindByBodyAndCarModelAndTransmission() {
        store.add(post);
        List<SearchAttribute> cc = List.of(body, carModel, transmission);
        List<Post> posts = store.findBySearchAttributes(cc);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        assertThat(posts.get(0).getCar().getBody().getName()).isEqualTo("Body1");
        assertThat(posts.get(0).getCar().getCarModel().getName()).isEqualTo("CarModel1");
        assertThat(posts.get(0).getCar().getTransmission().getName()).isEqualTo("Transmission1");

        Transmission transmission2 = new Transmission();
        transmission2.setName("Transmission2");
        cc = List.of(body, carModel, transmission2);
        posts = store.findBySearchAttributes(cc);
        assertThat(posts).isEmpty();
    }

    @Test
    void whenFindByPublish() {
        store.add(post);
        PostIsPublish publish = new PostIsPublish(true);
        List<Post> posts = store.findBySearchAttributes(List.of(publish));
        posts.forEach(System.out::println);
        assertThat(posts).isNotEmpty().hasSize(1).contains(post);
        assertThat(posts.get(0).isPublish()).isEqualTo(true);
        posts = store.findBySearchAttributes(List.of(new PostIsPublish(false)));
        assertThat(posts).isEmpty();
    }


    @Test
    void whenFindBySubscribedUser() {
        System.out.println("post1 before = " + post);

        store.add(post);
        System.out.println("post1 after = " + post);

        Post post2 = new Post();
        post2.setUsers(List.of(user, user2));
        post2.setUser(user3);
        post2.setText("Text for post2");
        post2.setCar(car);
        post2.setPriceHistory(List.of(priceHistory));
        System.out.println("post2 before = " + post2);
        store.add(post2);
        System.out.println("post2 after = " + post2);
        List<Post> postsDB = store.findBySubscribedUser(user2);
        System.out.println("==========");
        postsDB.forEach(System.out::println);
        assertThat(postsDB).isNotEmpty().hasSize(2).contains(post, post2);
        postsDB = store.findBySubscribedUser(user);
        assertThat(postsDB).isNotEmpty().hasSize(1).contains(post2);

    }
}