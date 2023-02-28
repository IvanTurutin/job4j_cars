package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository repository;
    @Override
    public boolean add(Post post) {
        return repository.add(post).isPresent();
    }

    @Override
    public boolean update(Post post) {
        return repository.update(post);
    }

    @Override
    public boolean delete(int postId) {
        return repository.findById(postId).isPresent() && repository.delete(postId);
    }

    @Override
    public List<Post> findAll() {
        return repository.findAllOrderById();
    }

    @Override
    public List<Post> findAll(User user) {
        /*
        List<Task> tasks = findAll();
        if (user.getTimeZone() != null) {
            tasks.forEach(t -> setTimeZone(t, user.getTimeZone().getZoneId()));
        } else {
            tasks.forEach(this::setDefaultTimeZone);
        }
        return tasks;
         */
        return findAll();
    }

    @Override
    public Optional<Post> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Post> findLastDays(int days) {
        return repository.findLastDays(days);
    }

    @Override
    public List<Post> findBySubscribedUser(User user) {
        return repository.findBySubscribedUser(user);
    }

    @Override
    public List<Post> findBySearchAttributes(List<SearchAttribute> characts) {
        return repository.findBySearchAttributes(characts);
    }
}
