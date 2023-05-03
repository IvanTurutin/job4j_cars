package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;

import java.util.List;
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
    Optional<FileDto> save(FileDto fileDto);

    /**
     * Преобразует список из FileDto в список File при этом сохраняет все несохраненные файлы на диске
     * @param fileDtos список из FileDto
     * @return список File
     */
    List<File> fileDtoListToFileList(List<FileDto> fileDtos);

    /**
     * Преобразует список из File в список FileDto при этом загружает все файлы с диска
     * @param files список из File
     * @return список FileDto
     */
    List<FileDto> fileListToFileDtoList(List<File> files);

    /**
     * Преобразует список из File в список FileDto при этом не загружает файлы с диска
     * @param files список из File
     * @return список FileDto
     */
    List<FileDto> fileListToFileDtoListLight(List<File> files);

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