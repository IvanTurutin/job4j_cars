package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.model.Transmission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий типов коробки передач
 * @see CarModel
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlTransmissionRepository implements TransmissionRepository {
    private final CrudRepository cr;

    public static final String MODEL = "Transmission";
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
    public Optional<Transmission> add(Transmission transmission) {
        return cr.run(session -> session.save(transmission)) ? Optional.of(transmission) : Optional.empty();
    }

    @Override
    public Optional<Transmission> findById(int id) {
        return cr.optional(
                FIND_BY_ID_STATEMENT, Transmission.class,
                Map.of(ID, id)
        );
    }

    @Override
    public List<Transmission> findAll() {
        return cr.query(FIND_ALL_ORDER_BY_ID_STATEMENT, Transmission.class);
    }

    @Override
    public Optional<Transmission> delete(Transmission transmission) {
        return cr.query(
                DELETE_STATEMENT,
                Map.of(ID, transmission.getCharactValue())
        )
                ? Optional.of(transmission)
                : Optional.empty();
    }

    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        cr.run(TRUNCATE_TABLE, new HashMap<>());
    }

}
