package ru.job4j.cars.repository;

import ru.job4j.cars.model.File;

import java.util.Optional;

/**
 * Репозиторий файлов
 * @see ru.job4j.cars.model.File
 */
public interface FileRepository {
    /**
     * Добавляет File в репозиторий и назначает id
     * @param file File для добавления
     * @return File в Optional если успех, и Optional.empty() если неудача
     */
    Optional<File> save(File file);

    /**
     * Ищет File по идентификатору
     * @param id идентификатор File
     * @return File в Optional если успех, и Optional.empty() если неудача
     */
    Optional<File> findById(int id);

    /**
     * Удаляет File
     * @param id для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean deleteById(int id);
}