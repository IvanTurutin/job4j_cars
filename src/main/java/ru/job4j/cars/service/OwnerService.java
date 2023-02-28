package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

/**
 * Сервис собственников
 * @see Owner
 */
public interface OwnerService {
    /**
     * Обрабатывает запрос на добавление собственника
     * @param owner собственник для добавления
     * @return true если успех, и false если неудача
     */
    boolean add(Owner owner);

    /**
     * Обрабатывает запрос на поиск собственника по id
     * @param id идентификатор собственника
     * @return собственник в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Owner> findById(int id);

    /**
     * Обрабатывает запрос на поиск всех собственников
     * @return список всех найденных собственников
     */
    List<Owner> findAll();

    /**
     * Обрабатывает запрос на изменение собственника
     * @param owner собственник для изменения
     * @return true если обновлен, false если не обновлено
     */
    boolean update(Owner owner);

    /**
     * Обрабатывает запрос на удаление собственника
     * @param owner для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean delete(Owner owner);
}
