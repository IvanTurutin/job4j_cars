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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

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

    @Override
    public Optional<FileDto> save(FileDto fileDto) {
        Optional<File> fileOptional = repository.save(fileDtoToFile(fileDto));
        return fileOptional.map(this::fileToFileDto);
    }

    /**
     * Создает папку для хранения файлов
     * @param path путь для сохранения файлов
     */
    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<File> fileDtoListToFileList(List<FileDto> fileDtos) {
        return fileDtos
                .stream()
                .map(this::fileDtoToFile)
                .toList();
    }

    /**
     * Преобразует FileDto в File
     * @param fileDto объект FileDto для преобразования
     * @return преобразованный объект File
     */
    private File fileDtoToFile(FileDto fileDto) {
        if (fileDto.getId() == 0) {
            File file = new File();
            file.setId(fileDto.getId());
            file.setPath(getNewFilePath(fileDto.getName()));
            file.setName(getFileNameFromPath(file.getPath()));
            writeFileBytes(file.getPath(), fileDto.getContent());
            return file;
        }

        Optional<File> fileOptional = repository.findById(fileDto.getId());
        if (fileOptional.isEmpty()) {
            throw new IllegalArgumentException("File not found in DB");
        }

        return fileOptional.get();
    }

    /**
     * Извлекает имя файла из пути
     * @param path путь
     * @return имя файла
     */
    private String getFileNameFromPath(String path) {
        String[] splitedPath = path.split(Pattern.quote(System.getProperty("file.separator")));
        return splitedPath[splitedPath.length - 1];
    }

    /**
     * Генерирует путь для файла
     * @param sourceName имя файла
     * @return путь к файлу
     */
    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    /**
     * Записывает файл на диск
     * @param path путь до файла
     * @param content данные для сохранения
     */
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

    @Override
    public List<FileDto> fileListToFileDtoList(List<File> files) {
        return new ArrayList<>(files.stream().map(this::fileToFileDto).toList());
    }

    @Override
    public List<FileDto> fileListToFileDtoListLight(List<File> files) {
        return files.stream().map(this::fileToFileDtoLight).toList();
    }

    /**
     * Преобразует File в FileDto
     * @param file исходный File объект
     * @return сформированный FileDto объект
     */
    private FileDto fileToFileDto(File file) {
        return new FileDto(file.getId(), file.getName(), readFileAsBytes(file.getPath()));
    }

    /**
     * Преобразует File в FileDto без самого содержимого файла
     * @param file исходный File объект
     * @return сформированный FileDto объект
     */
    private FileDto fileToFileDtoLight(File file) {
        return new FileDto(file.getId(), file.getName(), new byte[0]);
    }

    /**
     * Чтение файла с диска
     * @param path путь
     * @return данные файла
     */
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
        return fileOptional.filter(file -> deleteFile(file.getPath()) && repository.deleteById(id)).isPresent();
    }

    /**
     * Удаление файла с диска
     * @param path путь
     * @return true если удален, false если не удален
     */
    private boolean deleteFile(String path) {
        boolean rslt;
        try {
            rslt = Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rslt;
    }
}