package ru.job4j.cars.dto;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * DTO для объектов типа File
 */
@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    /**
     * Идентификатор объекта
     */
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Имя файла
     */
    private String name;

    /**
     * Содержание файла
     */
    private byte[] content;

    public FileDto(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
}
