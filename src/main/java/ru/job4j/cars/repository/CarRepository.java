package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий автомобилей
 * @see ru.job4j.cars.model.Car
 */
public interface CarRepository {
    /**
     * Добавляет автомобиль в репозиторий и назначает id
     * @param car автомобиль для добавления
     * @return автомобиль в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Car> add(Car car);

    /**
     * Ищет автомобиль по идентификатору
     * @param id идентификатор автомобиля
     * @return автомобиль в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Car> findById(int id);

    /**
     * Ищет все автомобили
     * @return список всех найденных автомобилей
     */
    List<Car> findAll();

    /**
     * Изменяет автомобиль
     * @param car автомобиль для изменения
     * @return true если обновлен, false если не обновлено
     */
    boolean update(Car car);

    /**
     * Удаляет автомобиль
     * @param car для удаления
     * @return автомобиль в Optional если удалено, и Optional.empty() если не удалено
     */
    Optional<Car> delete(Car car);
}
