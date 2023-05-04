package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlPriceHistoryRepository implements PriceHistoryRepository {
    private final CrudRepository crudRepository;

    public static final String MODEL = "PriceHistory";
    public static final String ID = "fId";

    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format("from %s", MODEL);
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where id = :%s", ID);
    public static final String FIND_BY_POST_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where post_id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);

    @Override
    public Optional<PriceHistory> create(PriceHistory priceHistory) {
        return crudRepository.run(session -> session.save(priceHistory))
                ? Optional.of(priceHistory)
                : Optional.empty();
    }

    @Override
    public boolean delete(int priceHistoryId) {
        return crudRepository.query(
                DELETE_STATEMENT,
                Map.of(ID, priceHistoryId)
        );
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return crudRepository.optional(
                FIND_BY_ID_STATEMENT, PriceHistory.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<PriceHistory> findByPost(Post post) {
        return crudRepository.query(
                FIND_BY_POST_STATEMENT, PriceHistory.class,
                Map.of(ID, post.getId())
        );
    }

    /**
     * Очищает таблицу от записей
     */
    public void truncate() {
        crudRepository.run(
                TRUNCATE_TABLE,
                new HashMap<>());
    }

}
