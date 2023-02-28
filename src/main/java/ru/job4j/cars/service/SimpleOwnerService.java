package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleOwnerService implements OwnerService {

    private final OwnerRepository repository;

    @Override
    public boolean add(Owner owner) {
        return repository.add(owner).isPresent();
    }

    @Override
    public Optional<Owner> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Owner> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean update(Owner owner) {
        return repository.update(owner);
    }

    @Override
    public boolean delete(Owner owner) {
        return repository.findById(owner.getId()).isPresent() && repository.delete(owner).isPresent();
    }
}
