package ru.job4j.cars.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO объектов типа Post
 */
@Component
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PostDto {

    /**
     * Идентификатор объявления
     */
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Текст объявления
     */
    private String text;
    /**
     * Время создания объявления
     */
    private LocalDateTime created = LocalDateTime.now();

    /**
     * Пользователь, создавший объявление
     */
    private Integer userId;
    private String userName;

    /**
     * Автомобиль, который продается в этом объявлении
     */
    private Car car;

    /**
     * Текущая цена на машину
     */
    private int price;

    /**
     * Фотографии автомобиля в виде FileDto
     */
    private List<FileDto> files = new ArrayList<>();

    /**
     * Фотографии автомобиля в виде MultipartFile
     */
    private List<MultipartFile> mpFiles = new ArrayList<>();

    /**
     * Индикатор отображаемости объявления в общем поиске
     */
    private boolean publish = false;

    /**
     * Добавляет файл в объявление
     * @param file объект файла FileDto
     */
    public void addFile(FileDto file) {
        files.add(file);
    }

    /**
     * Добавляет список файлов
     * @param fileDtos список файлов FileDto
     */
    public void addFileDtos(List<FileDto> fileDtos) {
        files.addAll(fileDtos);
    }

    /**
     * Добавляет список файлов
     * @param mPFiles список файлов MultipartFile
     */
    public void addFiles(List<MultipartFile> mPFiles) {
        for (MultipartFile mPFile : mPFiles) {
            try {
                FileDto fileDto = new FileDto(mPFile.getOriginalFilename(), mPFile.getBytes());
                if (fileDto.getContent().length != 0) {
                    this.addFile(fileDto);
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
    }

    /**
     * Создает минимально необходимый для создания объявления объект
     * @return объект PostDto
     */
    public PostDto getEmpty() {
        PostDto post = new PostDto();
        Car car = new Car();
        car.setCarModel(new CarModel());
        car.setOwner(new Owner());
        car.setBody(new Body());
        car.setEngine(new Engine());
        car.setTransmission(new Transmission());
        post.setCar(car);
        return post;
    }

    @Override
    public String toString() {
        return "PostDto{"
                + "id=" + id
                + ", text='" + text + '\''
                + ", created=" + created
                + ", userId=" + userId
                + ", userName='" + userName + '\''
                + ", car=" + car
                + ", files=" + files.stream().map(b -> "[id=" + b.getId() + ", name=" + b.getName() + ", content=[" + b.getContent().length + "]bytes]").toList()
                + ", publish=" + publish
                + '}';
    }
}
