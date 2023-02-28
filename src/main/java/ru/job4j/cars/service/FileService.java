package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;

import java.util.Optional;

/**
 * Сервис файлов
 * @see File
 */
public interface FileService {
    /**
     * Обрабатывает запрос на добавление файла
     * @param fileDto DTO файла для добавления
     * @return файл в Optional если успех, и Optional.empty() если неудача
     */
    Optional<File> save(FileDto fileDto);

    /**
     * Обрабатывает запрос на поиск файла по id
     * @param id идентификатор File
     * @return DTO файла
     */
    Optional<FileDto> findById(int id);

    /**
     * Обрабатывает запрос на удаление фойла по id
     * @param id для удаления
     * @return true если удалено, и false если не удалено
     */
    boolean deleteById(int id);
}