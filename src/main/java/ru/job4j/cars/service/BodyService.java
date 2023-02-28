package ru.job4j.cars.service;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

/**
 * Сервис типов кузовов
 * @see Body
 */
public interface BodyService {
    /**
     * Обрабатывает добавление кузова
     * @param body кузов для добавления
     * @return true если успех, и false если неудача
     */
    boolean add(Body body);

    /**
     * Обрабатывает поиск кузова по идентификатору
     * @param id идентификатор кузова
     * @return кузов в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Body> findById(int id);

    /**
     * Обрабатывает поиск всех кузовов
     * @return список всех найденных кузовов
     */
    List<Body> findAll();

    /**
     * Обрабатывает удаление кузова
     * @param body для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean delete(Body body);
}
