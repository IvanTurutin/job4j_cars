package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий типов кузовов
 * @see Body
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlBodyRepository implements BodyRepository {
    private final CrudRepository cr;

    public static final String MODEL = "Body";
    public static final String ID = "fID";

    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format("from %s", MODEL);
    public static final String FIND_ALL_ORDER_BY_ID_STATEMENT = FIND_ALL_STATEMENT + " order by id";
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);

    @Override
    public Optional<Body> add(Body body) {
        return cr.run(session -> session.persist(body)) ? Optional.of(body) : Optional.empty();
    }

    @Override
    public Optional<Body> findById(int id) {
        return cr.optional(
                FIND_BY_ID_STATEMENT, Body.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<Body> findAll() {
        return cr.query(FIND_ALL_ORDER_BY_ID_STATEMENT, Body.class);
    }

    @Override
    public Optional<Body> delete(Body body) {
        return cr.query(
                DELETE_STATEMENT,
                Map.of(ID, body.getId())
        )
                ? Optional.of(body)
                : Optional.empty();
    }

    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        cr.run(TRUNCATE_TABLE, new HashMap<>());
    }

}
