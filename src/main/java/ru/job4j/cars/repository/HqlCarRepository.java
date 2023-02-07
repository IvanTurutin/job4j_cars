package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий задач
 * @see ru.job4j.cars.model.Car
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlCarRepository implements CarRepository {
    private final CrudRepository cr;

    public static final String MODEL = "Car";
    public static final String ID = "fID";
    public static final String ENGINE = "engine";
    public static final String OWNER = "owner";
    public static final String OWNERS = "owners";


    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format(
            "select distinct t from %s t JOIN FETCH t.%s JOIN FETCH t.%s JOIN FETCH t.%s",
            MODEL, ENGINE, OWNER, OWNERS
    );
    public static final String FIND_ALL_ORDER_BY_ID_STATEMENT = FIND_ALL_STATEMENT + " order by t.id";
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where t.id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);

    @Override
    public Optional<Car> add(Car car) {
        return cr.run(session -> session.persist(car)) ? Optional.of(car) : Optional.empty();
    }

    @Override
    public Optional<Car> findById(int id) {
        return cr.optional(
                FIND_BY_ID_STATEMENT, Car.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<Car> findAll() {
        return cr.query(FIND_ALL_ORDER_BY_ID_STATEMENT, Car.class);
    }

    @Override
    public boolean update(Car car) {
        return cr.run(session -> session.merge(car));
    }

    @Override
    public Optional<Car> delete(Car car) {
        return cr.query(
                DELETE_STATEMENT,
                Map.of(ID, car.getId())
        )
                ? Optional.of(car)
                : Optional.empty();
    }


    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        cr.run(TRUNCATE_TABLE, new HashMap<>());
    }

}
