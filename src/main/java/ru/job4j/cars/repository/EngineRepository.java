package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий двигателей
 * @see ru.job4j.cars.model.Engine
 */
public interface EngineRepository {
    /**
     * Добавляет двигатель в репозиторий и назначает id
     * @param engine двигатель для добавления
     * @return двигатель в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Engine> add(Engine engine);

    /**
     * Ищет двигатель по идентификатору
     * @param id идентификатор двигателя
     * @return двигатель в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Engine> findById(int id);

    /**
     * Ищет все двигатели
     * @return список всех найденных двигателей
     */
    List<Engine> findAll();

    /**
     * Удаляет двигатель
     * @param engine для удаления
     * @return двигатель в Optional если удалено, и Optional.empty() если не удалено
     */
    Optional<Engine> delete(Engine engine);
}
