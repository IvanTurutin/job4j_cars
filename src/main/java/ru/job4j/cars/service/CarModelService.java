package ru.job4j.cars.service;

import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Optional;

/**
 * Вервис марок автомобилей
 * @see CarModel
 */
public interface CarModelService {
    /**
     * Обрабатывает добавление марки в репозиторий и назначает id
     * @param carModel марка для добавления
     * @return true если успех, и false если неудача
     */
    boolean add(CarModel carModel);

    /**
     * Обрабатывае поисе марки по идентификатору
     * @param id идентификатор марки
     * @return марка в Optional если успех, и Optional.empty() если неудача
     */
    Optional<CarModel> findById(int id);

    /**
     * Обрабатывает поиск всех марки
     * @return список всех найденных марок
     */
    List<CarModel> findAll();

    /**
     * Обрабатывае удаление марки
     * @param carModel для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean delete(CarModel carModel);
}
