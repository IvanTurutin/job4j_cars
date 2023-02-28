package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository repository;

    @Override
    public boolean add(Car car) {
        return repository.add(car).isPresent();
    }

    @Override
    public Optional<Car> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean update(Car car) {
        return repository.update(car);
    }

    @Override
    public boolean delete(Car car) {
        return repository.findById(car.getId()).isPresent() && repository.delete(car).isPresent();
    }
}
