package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.SimpleCrudRepository;
import ru.job4j.cars.repository.HqlUserRepository;



public class UserUsage {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            CrudRepository cR = new SimpleCrudRepository(sf);
            var userRepository = new HqlUserRepository(cR);
            userRepository.truncate();
            var user = new User();
            user.setLogin("admin");
            user.setPassword("adminPass");
            var user2 = new User();
            user2.setLogin("user");
            user2.setPassword("userPass");
            userRepository.create(user);
            userRepository.create(user2);
            System.out.println("user id = " + user.getId());
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.findByLikeLogin("e")
                    .forEach(System.out::println);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.findByLoginAndPassword("admin", "pass")
                    .ifPresent(System.out::println);
            user.setPassword("password");
            userRepository.update(user);
            userRepository.findById(user.getId())
                    .ifPresent(System.out::println);
            userRepository.delete(user.getId());
            userRepository.findAllOrderById()
                    .forEach(System.out::println);
            userRepository.truncate();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}