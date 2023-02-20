package ru.job4j.cars.repository;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий типов кузовов
 * @see ru.job4j.cars.model.Body
 */
public interface BodyRepository {
    /**
     * Добавляет кузов в репозиторий и назначает id
     * @param body кузов для добавления
     * @return кузов в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Body> add(Body body);

    /**
     * Ищет кузов по идентификатору
     * @param id идентификатор кузова
     * @return кузов в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Body> findById(int id);

    /**
     * Ищет все кузова
     * @return список всех найденных кузовов
     */
    List<Body> findAll();

    /**
     * Удаляет кузов
     * @param body для удаления
     * @return кузов в Optional если удалено, и Optional.empty() если не удалено
     */
    Optional<Body> delete(Body body);
}
