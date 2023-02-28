package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * Сервис автомобилей
 * @see Car
 */
public interface CarService {
    /**
     * Обрабатывает добавление автомобиля
     * @param car автомобиль для добавления
     * @return true если успех, и false если неудача
     */
    boolean add(Car car);

    /**
     * Обрабатывает поиск автомобиля по идентификатору
     * @param id идентификатор автомобиля
     * @return автомобиль в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Car> findById(int id);

    /**
     * Обрабатывает поиск всех автомобилей
     * @return список всех найденных автомобилей
     */
    List<Car> findAll();

    /**
     * Обрабатывает изменение автомобиля
     * @param car автомобиль для изменения
     * @return true если обновлен, false если не обновлено
     */
    boolean update(Car car);

    /**
     * Обрабатывает удаление автомобиля
     * @param car для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean delete(Car car);
}
