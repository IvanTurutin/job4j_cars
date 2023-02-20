package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий марок автомобилей
 * @see ru.job4j.cars.model.CarModel
 */
public interface CarModelRepository {
    /**
     * Добавляет марку в репозиторий и назначает id
     * @param carModel марка для добавления
     * @return марка в Optional если успех, и Optional.empty() если неудача
     */
    Optional<CarModel> add(CarModel carModel);

    /**
     * Ищет марку по идентификатору
     * @param id идентификатор марки
     * @return марка в Optional если успех, и Optional.empty() если неудача
     */
    Optional<CarModel> findById(int id);

    /**
     * Ищет все марки
     * @return список всех найденных марок
     */
    List<CarModel> findAll();

    /**
     * Удаляет марку
     * @param carModel для удаления
     * @return марка в Optional если удалено, и Optional.empty() если не удалено
     */
    Optional<CarModel> delete(CarModel carModel);
}
