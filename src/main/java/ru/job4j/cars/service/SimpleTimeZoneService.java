package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.TimeZone;
import ru.job4j.cars.repository.TimeZoneRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleTimeZoneService implements TimeZoneService {

    private final TimeZoneRepository repository;

    @Override
    public boolean add(TimeZone timeZone) {
        return repository.add(timeZone).isPresent();
    }

    @Override
    public Optional<TimeZone> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<TimeZone> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean update(TimeZone timeZone) {
        return repository.update(timeZone);
    }

    @Override
    public boolean delete(int id) {
        Optional<TimeZone> optionalCategory = repository.findById(id);
        return optionalCategory.isPresent() && repository.delete(optionalCategory.get()).isPresent();
    }

    @Override
    public TimeZone checkTimeZone(int id) {
        Optional<TimeZone> timeZone = findById(id);
        if (timeZone.isEmpty()) {
            throw new IllegalArgumentException("Такого часового пояса нет в базе данных");
        }
        return timeZone.get();
    }

}
