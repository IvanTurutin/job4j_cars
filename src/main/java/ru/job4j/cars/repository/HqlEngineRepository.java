package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий задач
 * @see Car
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlEngineRepository implements EngineRepository {
    private final CrudRepository cr;

    public static final String MODEL = "Engine";
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
    public Optional<Engine> add(Engine engine) {
        return cr.run(session -> session.persist(engine)) ? Optional.of(engine) : Optional.empty();
    }

    @Override
    public Optional<Engine> findById(int id) {
        return cr.optional(
                FIND_BY_ID_STATEMENT, Engine.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<Engine> findAll() {
        return cr.query(FIND_ALL_ORDER_BY_ID_STATEMENT, Engine.class);
    }

    @Override
    public Optional<Engine> delete(Engine engine) {
        return cr.query(
                DELETE_STATEMENT,
                Map.of(ID, engine.getId())
        )
                ? Optional.of(engine)
                : Optional.empty();
    }


    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        cr.run(TRUNCATE_TABLE, new HashMap<>());
    }

}
