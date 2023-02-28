package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Transmission;
import ru.job4j.cars.repository.TransmissionRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleTransmissionService implements TransmissionService {

    private final TransmissionRepository repository;

    @Override
    public boolean add(Transmission transmission) {
        return repository.add(transmission).isPresent();
    }

    @Override
    public Optional<Transmission> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Transmission> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean delete(Transmission transmission) {
        return repository.findById(transmission.getId()).isPresent() && repository.delete(transmission).isPresent();
    }
}
