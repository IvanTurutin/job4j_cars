package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.BodyRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleBodyService implements BodyService {
    private final BodyRepository repository;
    @Override
    public boolean add(Body body) {
        return repository.add(body).isPresent();
    }

    @Override
    public Optional<Body> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Body> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Body body) {
        return repository.findById(body.getId()).isPresent() && repository.delete(body).isPresent();
    }
}
