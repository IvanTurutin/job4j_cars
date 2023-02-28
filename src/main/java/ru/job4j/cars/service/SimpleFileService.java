package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис файлов
 * @see File
 */
@ThreadSafe
@Service
public class SimpleFileService implements FileService {

    private final FileRepository repository;
    private final String storageDirectory;

    public SimpleFileService(FileRepository fileRepository,
                             @Value("${file.directory}") String storageDirectory) {
        this.repository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обрабатывает запрос на добавление файла
     * @param fileDto DTO файла для добавления
     * @return файл в Optional если успех, и Optional.empty() если неудача
     */
    @Override
    public Optional<File> save(FileDto fileDto) {
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        return repository.save(new File(fileDto.getName(), path));
    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обрабатывает запрос на поиск файла по id
     * @param id идентификатор File
     * @return DTO файла
     */
    public Optional<FileDto> findById(int id) {
        var fileOptional = repository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Обрабатывает запрос на удаление фойла по id
     * @param id для удаления
     * @return true если удалено, и false если не удалено
     */
    public boolean deleteById(int id) {
        var fileOptional = repository.findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        return repository.deleteById(id);
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}