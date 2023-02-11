package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.*;

import java.time.LocalDateTime;

public class PostUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            CrudRepository cR = new SimpleCrudRepository(sf);
            var userRepository = new HqlUserRepository(cR);
            var postRepository = new HqlPostRepository(cR);
            var priceHistoryRepository = new HqlPriceHistoryRepository(cR);

/*
            priceHistoryRepository.truncate();
            postRepository.truncate();
            userRepository.truncate();
*/
            var user = new User();
            user.setLogin("admin");
            user.setPassword("adminPass");
            var user2 = new User();
            user2.setLogin("user");
            user2.setPassword("userPass");
            userRepository.create(user);
            userRepository.create(user2);
            System.out.println("user id = " + user.getId());

            Post post = new Post();
            post.setCreated(LocalDateTime.now());
            post.setUser(user);
            post.setText("Post text");

            PriceHistory priceHistory1 = new PriceHistory();
            priceHistory1.setAfter(100);
            priceHistory1.setBefore(120);

            PriceHistory priceHistory2 = new PriceHistory();
            priceHistory2.setAfter(90);
            priceHistory2.setBefore(100);

/*
            post.setPriceHistory(List.of(priceHistory1, priceHistory2));

            postRepository.add(post);

            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeLogin("e")
                    .forEach(System.out::println);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.findByLogin("admin")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            userRepository.update(user);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);

            postRepository.findById(post.getId()).get().getPriceHistory().forEach(System.out::println);
*/
/*
            postRepository.truncate();
            userRepository.truncate();
*/

        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
