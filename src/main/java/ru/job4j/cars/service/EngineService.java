package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

/**
 * Сервис двигателей
 * @see Engine
 */
public interface EngineService {
    /**
     * Обрабатывает запрос на добавление двигателя
     * @param engine двигатель для добавления
     * @return true если успех, и false если неудача
     */
    boolean add(Engine engine);

    /**
     * Обрабатывает запрос на поиск двигателя по идентификатору
     * @param id идентификатор двигателя
     * @return двигатель в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Engine> findById(int id);

    /**
     * Обрабатывает запрос на поиск всех двигателей
     * @return список всех найденных двигателей
     */
    List<Engine> findAll();

    /**
     * Обрабатывает запрос на удаление двигателя
     * @param engine для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean delete(Engine engine);
}
