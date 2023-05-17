package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий марок автомобилей
 * @see ru.job4j.cars.model.CarModel
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlCarModelRepository implements CarModelRepository {
    private final CrudRepository cr;

    public static final String MODEL = "CarModel";
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
    public Optional<CarModel> add(CarModel carModel) {
        return cr.add(carModel);
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return cr.optional(
                FIND_BY_ID_STATEMENT, CarModel.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<CarModel> findAll() {
        return cr.query(FIND_ALL_ORDER_BY_ID_STATEMENT, CarModel.class);
    }

    @Override
    public Optional<CarModel> delete(CarModel carModel) {
        return cr.delete(
                DELETE_STATEMENT,
                Map.of(ID, carModel.getId()),
                carModel
        );
    }

    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        cr.run(TRUNCATE_TABLE, new HashMap<>());
    }

}
