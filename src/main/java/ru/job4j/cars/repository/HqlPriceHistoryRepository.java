package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@AllArgsConstructor
public class HqlPriceHistoryRepository implements PriceHistoryRepository {
    private final CrudRepository crudRepository;

    public static final String MODEL = "Post";
    public static final String ID = "fId";

    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format("from %s", MODEL);
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where id = :%s", ID);
    public static final String FIND_BY_POST_STATEMENT = FIND_ALL_STATEMENT
            + String.format(" where auto_user_id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);

    @Override
    public PriceHistory create(PriceHistory priceHistory) {
        crudRepository.run(session -> session.persist(priceHistory));
        return priceHistory;
    }

    @Override
    public void delete(int priceHistoryId) {
        crudRepository.run(
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

    public void truncate() {
        crudRepository.run(
                TRUNCATE_TABLE,
                new HashMap<>());
    }

}
