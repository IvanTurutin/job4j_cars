package ru.job4j.cars.repository;

import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий типов коробок передач
 * @see ru.job4j.cars.model.Transmission
 */
public interface TransmissionRepository {
    /**
     * Добавляет коробку в репозиторий и назначает id
     * @param transmission коробка для добавления
     * @return коробка в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Transmission> add(Transmission transmission);

    /**
     * Ищет коробку по идентификатору
     * @param id идентификатор коробки
     * @return коробка в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Transmission> findById(int id);

    /**
     * Ищет все коробки
     * @return список всех найденных коробок
     */
    List<Transmission> findAll();

    /**
     * Удаляет коробку
     * @param transmission для удаления
     * @return коробка в Optional если удалено, и Optional.empty() если не удалено
     */
    Optional<Transmission> delete(Transmission transmission);
}
