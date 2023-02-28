package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleEngineService implements EngineService {

    private final EngineRepository repository;

    @Override
    public boolean add(Engine engine) {
        return repository.add(engine).isPresent();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Engine> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Engine engine) {
        return repository.findById(engine.getId()).isPresent() && repository.delete(engine).isPresent();
    }
}
