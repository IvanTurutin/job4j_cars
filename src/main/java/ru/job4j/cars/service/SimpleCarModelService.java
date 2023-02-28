package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.CarModelRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleCarModelService implements CarModelService {

    private final CarModelRepository repository;

    @Override
    public boolean add(CarModel carModel) {
        return repository.add(carModel).isPresent();
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<CarModel> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(CarModel carModel) {
        return repository.findById(carModel.getId()).isPresent() && repository.delete(carModel).isPresent();
    }
}
