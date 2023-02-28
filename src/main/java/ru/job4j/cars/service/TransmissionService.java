package ru.job4j.cars.service;

import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Optional;

/**
 * Сервис типов коробок передач
 * @see Transmission
 */
public interface TransmissionService {
    /**
     * Обрабатывает запрос на добавление коробки
     * @param transmission коробка для добавления
     * @return true если добавлен, false если не добавлен
     */
    boolean add(Transmission transmission);

    /**
     * Обрабатывает запрос на поиск коробки
     * @param id идентификатор коробки
     * @return коробка в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Transmission> findById(int id);

    /**
     * Обрабатывает запрос на поиск всех коробок
     * @return список всех найденных коробок
     */
    List<Transmission> findAll();

    /**
     * Обрабатывает запро на удаление коробки
     * @param transmission для удаления
     * @return true если удален, false если не удален
     */
    boolean delete(Transmission transmission);
}
