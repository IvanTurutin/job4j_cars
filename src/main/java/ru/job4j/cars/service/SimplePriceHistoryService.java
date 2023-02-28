package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {

    private final PriceHistoryRepository repository;

    @Override
    public boolean create(PriceHistory priceHistory) {
        return repository.create(priceHistory).isPresent();
    }

    @Override
    public boolean delete(int priceHistoryId) {
        return repository.findById(priceHistoryId).isPresent() && repository.delete(priceHistoryId);
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<PriceHistory> findByPost(Post post) {
        return repository.findByPost(post);
    }
}
