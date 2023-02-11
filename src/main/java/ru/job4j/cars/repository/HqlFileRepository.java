package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Репозиторий файлов
 * @see ru.job4j.cars.model.File
 */
@ThreadSafe
@Repository
@AllArgsConstructor
public class HqlFileRepository implements FileRepository {

    private final CrudRepository cr;

    public static final String MODEL = "File";
    public static final String ID = "fID";

    public static final String DELETE_STATEMENT = String.format(
            "DELETE %s WHERE id = :%s",
            MODEL, ID
    );
    public static final String FIND_ALL_STATEMENT = String.format("from %s", MODEL);
    public static final String FIND_BY_ID_STATEMENT = FIND_ALL_STATEMENT + String.format(" where t.id = :%s", ID);
    public static final String TRUNCATE_TABLE = String.format("DELETE FROM %s", MODEL);

    @Override
    public Optional<File> save(File file) {
        return cr.run(session -> session.persist(file)) ? Optional.of(file) : Optional.empty();
    }

    @Override
    public Optional<File> findById(int id) {
        return cr.optional(
                FIND_BY_ID_STATEMENT, File.class,
                Map.of(ID, id)
        );
    }

    @Override
    public boolean deleteById(int id) {
        return cr.query(DELETE_STATEMENT, Map.of(ID, id));
    }

    /**
     * Очищает таблицу от записей
     */
    public void truncateTable() {
        cr.run(TRUNCATE_TABLE, new HashMap<>());
    }

}
