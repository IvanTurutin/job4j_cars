package ru.job4j.cars.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HibernateConfiguration;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class HqlPriceHistoryRepositoryTest {

    private static HqlPriceHistoryRepository store;
    private static Post post;
    private static PriceHistory priceHistory;
    private static PriceHistory priceHistory2;

    @BeforeAll
    public static void initStore() {
        CrudRepository cr = new SimpleCrudRepository(new HibernateConfiguration().sf());
        store = new HqlPriceHistoryRepository(cr);

        post = new Post();
        post.setText("Post1");
        User user = new User();
        user.setLogin("User1");
        post.setUser(user);

        Car car = new Car();
        Engine engine = new Engine();
        engine.setName("Engine1");
        car.setEngine(engine);
        Owner owner = new Owner();
        owner.setName("Owner1");
        /*car.setOwner(owner);*/
        Owner owner2 = new Owner();
        owner2.setName("Owner2");

        HqlOwnerRepository ownerRepository = new HqlOwnerRepository(cr);
        ownerRepository.truncateTable();
        ownerRepository.add(owner);
        ownerRepository.add(owner2);

        List<CarOwner> owners = new ArrayList<>();
        /*Set<CarOwner> owners = new HashSet<>();*/
        CarOwner carOwner = new CarOwner();
        carOwner.setOwner(owner);
        CarOwner carOwner2 = new CarOwner();
        carOwner2.setOwner(owner2);
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

        User user2 = new User();
        user2.setLogin("User2");
        User user3 = new User();
        user3.setLogin("User3");
        List<User> users = List.of(user2, user3);
        post.setUsers(users);

        File file = new File();
        file.setName("File1");
        File file2 = new File();
        file2.setName("File2");
        ArrayList<File> files = new ArrayList<>(List.of(file, file2));
        post.setFiles(files);

        System.out.println("Post at initStore = " + post);

        HqlPostRepository postRepository = new HqlPostRepository(cr);
        postRepository.add(post);
    }

    @Test
    void whenCreateAndFindById() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(200);
        priceHistory.setAfter(180);
        store.create(priceHistory);

        Optional<PriceHistory> priceHistoryDb = store.findById(priceHistory.getId());
        assertThat(priceHistoryDb).isPresent();
        assertThat(priceHistoryDb.get().getBefore()).isEqualTo(200);
        assertThat(priceHistoryDb.get().getAfter()).isEqualTo(180);

    }

    @Test
    void whenDelete() {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setBefore(200);
        priceHistory.setAfter(180);
        store.create(priceHistory);
        store.delete(priceHistory.getId());
        Optional<PriceHistory> priceHistoryDb = store.findById(priceHistory.getId());
        assertThat(priceHistoryDb).isEmpty();
    }

    @Test
    void findByPost() {
        List<PriceHistory> priceHistories = store.findByPost(post);
        assertThat(priceHistories).isNotEmpty().hasSize(2).contains(priceHistory, priceHistory2);
    }
}