package ru.job4j.cars.repository;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий собственников
 * @see ru.job4j.cars.model.Owner
 */
public interface OwnerRepository {
    /**
     * Добавляет собственнника в репозиторий и назначает id
     * @param owner собственник для добавления
     * @return собственник в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Owner> add(Owner owner);

    /**
     * Ищет собственника по идентификатору
     * @param id идентификатор собственника
     * @return собственник в Optional если успех, и Optional.empty() если неудача
     */
    Optional<Owner> findById(int id);

    /**
     * Ищет всех собственников
     * @return список всех найденных собственников
     */
    List<Owner> findAll();

    /**
     * Изменяет собственника
     * @param owner собственник для изменения
     * @return true если обновлен, false если не обновлено
     */
    boolean update(Owner owner);

    /**
     * Удаляет собственника
     * @param owner для удаления
     * @return собственник в Optional если удалено, и Optional.empty() если не удалено
     */
    Optional<Owner> delete(Owner owner);
}
